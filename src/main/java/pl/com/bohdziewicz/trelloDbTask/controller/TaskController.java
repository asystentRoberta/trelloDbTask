package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.TaskDTO;
import pl.com.bohdziewicz.trelloDbTask.mapper.TaskMapper;
import pl.com.bohdziewicz.trelloDbTask.service.DbService;

@RestController
@RequestMapping("/trelloDbTask")
public class TaskController {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getAllTasks")
    List<TaskDTO> getAllTasks() {

        return taskMapper.mapTaskListToTaskDtoList(dbService.getAllTask());
    }
}
