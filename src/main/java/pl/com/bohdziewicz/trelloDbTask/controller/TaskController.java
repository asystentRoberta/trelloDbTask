package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.TaskDTO;
import pl.com.bohdziewicz.trelloDbTask.mapper.TaskMapper;
import pl.com.bohdziewicz.trelloDbTask.service.DbService;

@RestController
@RequestMapping("/trelloDbTask")
public class TaskController {

    private final DbService dbService;
    private final TaskMapper taskMapper;

    public TaskController(DbService dbService, TaskMapper taskMapper) {

        this.dbService = dbService;
        this.taskMapper = taskMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllTasks")
    List<TaskDTO> getAllTasks() {

        return taskMapper.mapTaskListToTaskDtoList(dbService.getAllTask());
    }
}
