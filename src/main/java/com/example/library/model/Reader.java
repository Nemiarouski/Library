package com.example.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 3, max = 20, message = "Name can be between 3 and 20 characters.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname can't be empty.")
    @Size(min = 3, max = 20, message = "Surname can be between 3 and 20 characters.")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Login can't be empty.")
    @Size(min = 3, max = 20, message = "Login can be between 3 and 20 characters.")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Password can't be empty.")
    @Column(name = "password")
    private String password;

    @AssertTrue
    @Column(name = "enable")
    private Boolean enable;

    @Pattern(regexp = "(ROLE_READER|ROLE_ADMIN)")
    @NotEmpty(message = "Authority can't be empty.")
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