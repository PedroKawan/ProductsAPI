package me.pedrokaua.securityproject.controllers;

import jakarta.validation.Valid;
import me.pedrokaua.securityproject.dtos.UserRecord;
import me.pedrokaua.securityproject.exceptions.UserAlreadyExistsException;
import me.pedrokaua.securityproject.models.entities.UserModel;
import me.pedrokaua.securityproject.models.services.UserDetailsServiceImpl;
import me.pedrokaua.securityproject.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public ResponseEntity<List<UserRecord>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "n") String username){
        try {
            var userRecord = new UserRecord((UserModel) userDetailsService.loadUserByUsername(username));
            return ResponseEntity.status(HttpStatus.FOUND).body(userRecord);
        } catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with name: " + username);
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserModel entity) {
        try {
            Object response = userService.saveUser(entity);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

