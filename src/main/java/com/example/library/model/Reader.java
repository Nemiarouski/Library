package com.example.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
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

    public Reader(String name, String surname, String login, String password, Boolean enable, String authority) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.enable = enable;
        this.authority = authority;
    }

    public void copyOf(Reader reader) {
        this.setName(reader.getName());
        this.setSurname(reader.getSurname());
        this.setLogin(reader.getLogin());
        this.setPassword(reader.getPassword());
    }
}