package com.bytes.bytes.contexts.kitchen.domain.models;

public class User {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private String password;
    private boolean active;

    public User() {}

    public User(Long id, String name, String email, UserRole role, String password, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();

        validate();
    }

    public void validate(){
        if(this.name == null || this.name.isEmpty()){
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        if(this.email == null || this.email.isEmpty()){
            throw new IllegalArgumentException("O email é obrigatório");
        }
        if(this.password == null || this.password.isEmpty()){
            throw new IllegalArgumentException("A senha é obrigatório");
        }

        if(this.password.length() < 8 || this.password.length() > 12){
            throw new IllegalArgumentException("A senha deve ter entre 8 caracteres a 12 caracteres");
        }

        if(!this.email.contains("@") || !this.email.contains(".") || this.email.length() < 5 || this.email.contains(" ")){
            throw new IllegalArgumentException("O email é inválido");
        }
    }
}
