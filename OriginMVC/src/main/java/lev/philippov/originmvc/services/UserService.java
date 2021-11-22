package lev.philippov.originmvc.services;

import lev.philippov.originmvc.models.PrivateDetails;
import lev.philippov.originmvc.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User saveNewUser(String username, String password);
    void saveDetails(Long userId, PrivateDetails details) throws UsernameNotFoundException;
    User findById(Long userId) throws UsernameNotFoundException;
}
