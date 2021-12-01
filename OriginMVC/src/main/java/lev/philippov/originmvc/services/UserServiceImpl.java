package lev.philippov.originmvc.services;

import lev.philippov.originmvc.exceptions.UserAlreadyExistException;
import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.PrivateDetails;
import lev.philippov.originmvc.models.Role;
import lev.philippov.originmvc.models.User;
import lev.philippov.originmvc.repositories.OrderRepository;
import lev.philippov.originmvc.repositories.RoleRepository;
import lev.philippov.originmvc.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private OrderService orderService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUsername(username.toLowerCase()));
        return userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s is not found!", username)));
    }

    @Override
    public User findById(Long userId) throws UsernameNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(String.format("User with user Id %d is not found!", userId)));
    }

    @Transactional
    @Override
    public User saveNewUser(String username, String password) throws UserAlreadyExistException, UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username.toLowerCase());
        if (user != null) {
            throw new UserAlreadyExistException(String.format("User with username '%s' already exists!", username),user);
        }
        user = new User();
        user.setUsername(username.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        //ROLE_USER is HARDCODED!
        Role role = roleRepository.findByName("ROLE_USER");
        System.out.println("Role is " + role);
        user.setRoles(new HashSet<>(List.of(role)));
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public void saveDetails(Long userId, PrivateDetails details) throws  UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(String.format("User with Id = %d is not found!", userId)));
        user.setPrivateDetails(details);
        userRepository.save(user);

        //реализация простейшего механизма автоподвязвания заказов по номеру телефона

        Set<Order> orders = orderService.findAllByOrderDetailsPhone(details.getPhone());
        for (Order order : orders) {
            order.setUser(user);
        }
        orderService.saveAll(orders);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityGraph<?> user_with_roles = entityManager.getEntityGraph("user_with_roles");
        User user = entityManager.createQuery("SELECT u FROM User u where u.username=:username", User.class)
                .setParameter("username", username).setHint("javax.persistence.loadgraph", user_with_roles)
                .getSingleResult();
        if(user == null) {
            throw new UsernameNotFoundException("Invalid credentials.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

//    @Transactional
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username);
//        System.out.println(user);
//        if(user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
