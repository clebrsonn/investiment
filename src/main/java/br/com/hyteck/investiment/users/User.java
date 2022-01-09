package br.com.hyteck.investiment.users;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {

    private String name;

    private String document;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastsync;

}
