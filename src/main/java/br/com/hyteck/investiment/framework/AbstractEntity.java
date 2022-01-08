package br.com.hyteck.investiment.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public class AbstractEntity {
    @Id
    protected String id;

    public AbstractEntity(){
        if(getId()== null){
            setId(UUID.randomUUID().toString());
        }
    }
}