package me.pedrokaua.securityproject.enums;

public enum RoleTypes {
    ROLE_USER("ROLE_ADMIN"),
    ROLE_ADMIN("ROLE_USER"),
    ROLE_VISITOR("ROLE_VISITOR");

    private String name;

    RoleTypes(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
