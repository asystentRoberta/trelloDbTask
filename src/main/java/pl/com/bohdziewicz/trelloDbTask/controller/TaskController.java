package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.TaskDTO;
import pl.com.bohdziewicz.trelloDbTask.mapper.TaskMapper;
import pl.com.bohdziewicz.trelloDbTask.service.DbService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    @RequestMapping(method = RequestMethod.POST, value = "createSingleTask", consumes = APPLICATION_JSON_VALUE)
    String createSingleTask(@RequestBody TaskDTO taskDTO){
        dbService.saveSingleTask(taskMapper.mapTaskDtoToTask(taskDTO));
        return "Ok";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAllTasks")
    String deleteAllTasks() {

        dbService.deleteAllTasks();
        return "Done";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteSingleTaskById")
    String deleteSingleTask(@RequestParam Long taskId) {

        if (dbService.deleteTask(taskId)) {
            return "Deleted";
        }
        return "Not exist";
    }

}
