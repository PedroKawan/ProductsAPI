package me.pedrokaua.securityproject.security;

import me.pedrokaua.securityproject.models.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    private PasswordEncoder passwordEncoder
            = new BCryptPasswordEncoder();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);
        return http
                .authorizeHttpRequests(c -> {
                   /* c.requestMatchers("/").permitAll();
                    c.requestMatchers("/logout").permitAll();
                    c.requestMatchers(HttpMethod.GET, "/api/products").permitAll();
                    c.anyRequest().authenticated();*/
                    c.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

    /* ---- IN MEMORY ------*/
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("102030")
                .roles("ADMIN", "USER")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("405060")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user, user2);
    }

}
