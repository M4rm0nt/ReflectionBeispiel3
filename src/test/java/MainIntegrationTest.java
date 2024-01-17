import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class MainIntegrationTest {

    @Test
    void testMainOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        JsonSerializer.main(new String[]{});

        String output = outContent.toString();
        assertAll(
                () -> assertTrue(output.contains("\"alter\":32")),
                () -> assertTrue(output.contains("\"name\":\"Celine\"")),
                () -> assertTrue(output.contains("\"hobbies\":[\"Lesen\",\"Schwimmen\"]"))
        );
    }
}
