package com.example.library.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "authority")
    private String authority;

    public Reader() {}
    public Reader(String name, String surname, String login, String password, Boolean enable, String authority) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.enable = enable;
        this.authority = authority;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id) && Objects.equals(name, reader.name) && Objects.equals(surname, reader.surname) && Objects.equals(login, reader.login) && Objects.equals(password, reader.password) && Objects.equals(enable, reader.enable) && Objects.equals(authority, reader.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, enable, authority);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleType='" + authority + '\'' +
                '}';
    }
}