package pl.com.bohdziewicz.trelloDbTask.additionalTests;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnvironmentSetTests {

    @Test
    public void checkIsEnvironmentAreSetAtHostSystem() {

        assertNotNull(System.getenv("trelloAppUser"));
        assertNotNull(System.getenv("dbTrelloPassword"));
    }
}
