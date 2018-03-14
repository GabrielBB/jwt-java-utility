package com.github.gabrielbb.models;

/**
 * Created by Gabriel Basilio Brito on 3/13/2018.
 */
public class User {

    private String id;
    private Role role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}