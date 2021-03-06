package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
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

    @RequestMapping(method = RequestMethod.GET, value = "getSingleTaskById")
    TaskDTO getSingleTaskById(@RequestParam Long taskId) throws TaskNotFoundException {

        return taskMapper.mapTaskToDtoTask(
                dbService
                        .findSingleTaskById(taskId)
                        .orElseThrow(() -> new TaskNotFoundException("No task with such Id")));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createSingleTask",
            consumes = APPLICATION_JSON_VALUE)
    String createSingleTask(@RequestBody TaskDTO taskDTO) {

        dbService.saveSingleTask(taskMapper.mapTaskDtoToTask(taskDTO));
        return "Ok";
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "updateExistingTask",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public String updateExistingTask(@RequestBody TaskDTO taskDTO) {

        if (dbService.updateSingleTask(taskMapper.mapTaskDtoToTask(taskDTO))) {
            return "{\"Success\":1}";
        } else {
            return "{\"No such id\":0}";
        }
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
        } else {
            return "Not exist";
        }
    }
}
