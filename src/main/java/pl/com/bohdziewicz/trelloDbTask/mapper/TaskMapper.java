package pl.com.bohdziewicz.trelloDbTask.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.com.bohdziewicz.trelloDbTask.domain.Task;
import pl.com.bohdziewicz.trelloDbTask.domain.TaskDTO;

@Component
public class TaskMapper {

    public Task mapTaskDtoToTask(final TaskDTO taskDto) {

        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getContent()
        );
    }

    public TaskDTO mapTaskToDtoTask(final Task task) {

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getContent()
        );
    }

    public List<TaskDTO> mapTaskListToTaskDtoList(final List<Task> taskList) {

        return taskList.stream()
                .map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getContent()))
                .collect(Collectors.toList());
    }
}
