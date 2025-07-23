package org.example;

public class Courier {
    private String login;
    private String password;
    private String firstName;
    private Integer id;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }


    public Courier setId(Integer id) {
        this.id = id;
        return this;
    }
}
