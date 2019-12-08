package pl.com.bohdziewicz.trelloDbTask.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import pl.com.bohdziewicz.trelloDbTask.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();

    @Override
    void deleteById(Long id);

    @Override
    boolean existsById(Long id);

    @Override
    Optional<Task> findById(Long id);
}
