package me.pedrokaua.securityproject.services;

import me.pedrokaua.securityproject.dtos.UserRecord;
import me.pedrokaua.securityproject.entities.UserModel;
import me.pedrokaua.securityproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<Object> saveUser(UserModel entity){
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in Request-Body of the User!");
        }
        if (userRepository.findOne(Example.of(entity)).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Already Exists!");
        }

        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));

        userRepository.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    public ResponseEntity<List<UserRecord>> findAll(){
        List<UserRecord> list = userRepository.findAll().stream().map(UserRecord::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
