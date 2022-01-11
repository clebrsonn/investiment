package br.com.hyteck.investiment.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Email
    private String username;

    private String name;
    @CPF
    private String document;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastsync;

    private String password;

    private Boolean enabled=true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "authorities",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private List<Role> authorities;

}
