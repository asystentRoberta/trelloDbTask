package pl.com.bohdziewicz.trelloDbTask.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.domain.Task;
import pl.com.bohdziewicz.trelloDbTask.repository.TaskRepository;

@Service
public class DbService {

    private final TaskRepository taskRepository;

    public DbService(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTask() {

        return taskRepository.findAll();
    }

    public void saveSingleTask(Task task) {

        taskRepository.save(task);
    }

    public void deleteAllTasks() {

        taskRepository.deleteAll();
    }

    public boolean deleteTask(Long taskId) {

        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }
}
