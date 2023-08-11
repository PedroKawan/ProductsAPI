package me.pedrokaua.securityproject.dtos;

import me.pedrokaua.securityproject.entities.UserModel;

public record UserRecord(String id, String username, String password) {

    public UserRecord(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserRecord(UserModel entity){
        this(entity.getId().toString(), entity.getUsername(), entity.getPassword());
    }
}
