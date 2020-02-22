package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.UserAdapter;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.repository.UserRepository;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Role getUserRoleByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"))
                .getRole();
    }

    public Long getFundsByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"))
                .getFunds();
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s + "Not found"));
        log.warn("Login attempt username " + user.getUsername());
        return new UserAdapter(user);
    }

}
