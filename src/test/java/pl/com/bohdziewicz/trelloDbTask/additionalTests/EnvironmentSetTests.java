package pl.com.bohdziewicz.trelloDbTask.additionalTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnvironmentSetTests {

    @Test
    void checkIsEnvironmentAreSetAtHostSystem() {

        assertNotNull(System.getenv("trelloAppUser"));
        assertNotNull(System.getenv("dbTrelloPassword"));
    }
}
