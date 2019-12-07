package pl.com.bohdziewicz.trelloDbTask.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.com.bohdziewicz.trelloDbTask.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();
}
