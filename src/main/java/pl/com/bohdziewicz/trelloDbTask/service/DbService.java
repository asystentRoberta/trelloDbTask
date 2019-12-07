package pl.com.bohdziewicz.trelloDbTask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.domain.Task;
import pl.com.bohdziewicz.trelloDbTask.repository.TaskRepository;

@Service
public class DbService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTask() {

        return taskRepository.findAll();
    }
}
