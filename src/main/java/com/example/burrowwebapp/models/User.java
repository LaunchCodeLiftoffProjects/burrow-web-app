package com.example.burrowwebapp.models;

<<<<<<< HEAD
import com.sun.istack.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
public class User{

>>>>>>> login

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

<<<<<<< HEAD
    public String getUsername() {
        return username;
=======
    @Id
    @GeneratedValue
    private int id;


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
>>>>>>> login
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

<<<<<<< HEAD
}
=======


    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public int getId(){
        return id;
    }

}

>>>>>>> login
