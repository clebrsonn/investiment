package br.com.hyteck.investiment.framework;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class AbstractController<Entity, Id, Service extends AbstractService<Entity, Id, ?>>{
    private Service service;

    @GetMapping
    protected ResponseEntity<Collection<Entity>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{id}")
    protected ResponseEntity<Entity> findById(@PathVariable Id id) {
        Optional<Entity> entity = service.findById(id);

        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    protected ResponseEntity<Entity> save(@RequestBody Entity entity){
        return ResponseEntity.ok(service.save(entity));
    }

    @PutMapping
    protected ResponseEntity<Entity> update(Id id, Entity user) {
        Optional<Entity> savedUser = service.findById(id);
        if (savedUser.isPresent()) {
            //BeanUtils vários métodos úteis, copyproperties serve para copiar os atributos de uma classe para outro
            BeanUtils.copyProperties(user, savedUser.get(), "id");
            return ResponseEntity.ok(service.save(savedUser.get()));
        } else {
            throw new EmptyResultDataAccessException(1);
        }

    }

    @DeleteMapping
    protected ResponseEntity<Entity> delete(Id id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected Service getService(){
        return service;
    }

}
