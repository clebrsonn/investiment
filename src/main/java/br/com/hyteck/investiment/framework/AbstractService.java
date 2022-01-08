package br.com.hyteck.investiment.framework;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractService<Entity, Id> {
    private final JpaRepository<Entity, Id> repository;

    protected AbstractService(JpaRepository<Entity, Id> repository) {
        this.repository = repository;
    }

    public Optional<Entity> findById(Id id) {
        return repository.findById(id);
    }

    public Collection<Entity> findAll() {
        return repository.findAll();
    }

    public List<Entity> saveAll(Collection<Entity> entities) {
        entities = entities.parallelStream().map(this::validateSave).filter(Objects::nonNull).collect(Collectors.toList());
        return repository.saveAll(entities);
    }

    public Entity save(Entity entity) {
        entity = validateSave(entity);
        return repository.saveAndFlush(entity);
    }

    public abstract Entity validateSave(Entity entity);

    public void deleteById(Id id){
        repository.deleteById(id);
    }

    public void delete(Entity entity){
        repository.delete(entity);
    }

    public JpaRepository<Entity, Id> getRepository(){
        return this.repository;
    }
}
