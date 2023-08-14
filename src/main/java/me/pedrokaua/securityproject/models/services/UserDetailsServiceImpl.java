package me.pedrokaua.securityproject.models.services;

import me.pedrokaua.securityproject.models.entities.UserModel;
import me.pedrokaua.securityproject.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder
            = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        if (user != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(authority);
            return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        }

        return null;
    }
}
