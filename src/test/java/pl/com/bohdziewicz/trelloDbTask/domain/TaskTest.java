package pl.com.bohdziewicz.trelloDbTask.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    private final Long biggestLong = (long) Math.pow(2, 63);
    private final Task task = new Task(biggestLong, "testTitle", "testContent");

    @Test
    public void getId() {

        assertEquals(9223372036854775807L, task.getId());
    }

    @Test
    public void getTitle() {

        assertEquals("testTitle", task.getTitle());
    }

    @Test
    public void getContent() {

        assertEquals("testContent", task.getContent());
    }
}