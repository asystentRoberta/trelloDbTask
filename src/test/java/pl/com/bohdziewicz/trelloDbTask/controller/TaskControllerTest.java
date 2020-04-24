package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.com.bohdziewicz.trelloDbTask.domain.TaskDTO;
import pl.com.bohdziewicz.trelloDbTask.mapper.TaskMapper;
import pl.com.bohdziewicz.trelloDbTask.service.DbService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DbService dbService;
    @MockBean
    TaskMapper taskMapper;

    @Test
    public void shouldGetAllTskFromMockedDataBase() throws Exception {

        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(new TaskDTO(1L, "DtoTask", "DtoTaskContent"));

        when(taskMapper.mapTaskListToTaskDtoList(anyList())).thenReturn(taskDTOList);

        //When & Then
        mockMvc.perform(get("/trelloDbTask/getAllTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("DtoTask")))
                .andExpect(jsonPath("$[0].content", is("DtoTaskContent")));
    }
}