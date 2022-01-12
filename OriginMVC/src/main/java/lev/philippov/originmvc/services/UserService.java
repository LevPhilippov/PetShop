package lev.philippov.originmvc.services;

import lev.philippov.originmvc.domain.Role;
import lev.philippov.originmvc.exceptions.UserAlreadyExistException;
import lev.philippov.originmvc.repositories.RoleRepository;
import lev.philippov.originmvc.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<lev.philippov.originmvc.domain.User> byPhone = userRepository.findByPhone(username);
        lev.philippov.originmvc.domain.User savedUser = byPhone.orElseThrow(() -> new UsernameNotFoundException("User with such phone number doesn't exist."));
        UserDetails userDetails = User.withUsername(savedUser.getPhone()).password(savedUser.getPassword()).authorities(mapRolesToAuthorities(savedUser.getRoles())).build();
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    public void saveNewUser(String phone, String password) throws UserAlreadyExistException {
        lev.philippov.originmvc.domain.User user = new lev.philippov.originmvc.domain.User();
        user.setPhone(phone);
        user.setPassword(encoder.encode(password));
        user.setRoles(List.of(roleRepository.getRoleByRole("ROLE_USER")));
        Optional<lev.philippov.originmvc.domain.User> byPhone = userRepository.findByPhone(phone);
        if(byPhone.isPresent()) {
            throw new UserAlreadyExistException("User with such phone number already exists!");
        } else {
            userRepository.save(user);
        }
    }

}
