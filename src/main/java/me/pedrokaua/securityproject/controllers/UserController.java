package me.pedrokaua.securityproject.controllers;

import jakarta.validation.Valid;
import me.pedrokaua.securityproject.dtos.UserRecord;
import me.pedrokaua.securityproject.entities.UserModel;
import me.pedrokaua.securityproject.services.UserDetailsServiceImpl;
import me.pedrokaua.securityproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return userService.findAll();
    }

    @GetMapping(value = "/")
    public ResponseEntity<UserRecord> findByName(@RequestParam(value = "n") String username){
        var userRecord = new UserRecord((UserModel) userDetailsService.loadUserByUsername(username));
        return ResponseEntity.status(HttpStatus.FOUND).body(userRecord);
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserModel entity){
        return userService.saveUser(entity);
    }
}
