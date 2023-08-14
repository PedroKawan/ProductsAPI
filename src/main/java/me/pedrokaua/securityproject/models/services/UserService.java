package me.pedrokaua.securityproject.models.services;

import me.pedrokaua.securityproject.dtos.UserRecord;
import me.pedrokaua.securityproject.exceptions.UserAlreadyExistsException;
import me.pedrokaua.securityproject.models.entities.UserModel;
import me.pedrokaua.securityproject.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    
    @Autowired
    private UserRepository userRepository;


    public Object saveUser(UserModel entity) throws NullPointerException, UserAlreadyExistsException{

        List<UserModel> userList = userRepository.findAll();

        if (entity == null) {
            throw new NullPointerException("Error in Request-Body of the User!");
        }
        if (userList.contains(entity)){
            throw new UserAlreadyExistsException("User Already Exists in DataBase!");
        }

        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));

        userRepository.save(entity);
        return entity;
    }

    public List<UserRecord> findAll(){
        return userRepository.findAll().stream().map(UserRecord::new).toList();

    }

}
