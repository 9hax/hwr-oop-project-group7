package hwr.oop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class IOAdapterTest {

    @Test
    void createConsoleIOAdapterTest() {
        IOAdapter ioAdapter = new ConsoleIOAdapter();
        assertThat(ioAdapter).isNotNull();
    }
    @Test
    void createMockIOTest() {
        IOAdapter ioAdapter = new MockIOAdapter();
        assertThat(ioAdapter).isNotNull();
    }
    @Test
    void test_input() {
        IOAdapter ioAdapter = new ConsoleIOAdapter();
        String inputString = "This is a test string.";
        String testInput = String.format(inputString, System.lineSeparator(), System.lineSeparator());
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));

        String returned = ioAdapter.getString();

        assertThat(returned).isEqualTo(inputString);
    }

    @Test
    void test_output() {
        IOAdapter ioAdapter = new ConsoleIOAdapter();
        String outputString = "This is another test string.";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        ioAdapter.putString(outputString);

        String[] outputLines = outputStream.toString().split(System.lineSeparator());
        String lastOutput = outputLines[outputLines.length-1];

        assertThat(lastOutput).isEqualTo(outputString);
    }

    @Test
    void testInputMock() {
        IOAdapter ioAdapter = new MockIOAdapter();
        String inputString = "This is a test string.";
        assertThat(ioAdapter.getString()).isEmpty();
        ioAdapter.queueInput(inputString);

        String returned = ioAdapter.getString();

        assertThat(returned).isEqualTo(inputString);
    }

    @Test
    void test_outputMock() {
        IOAdapter ioAdapter = new MockIOAdapter();
        String outputString = "This is another test string.";

        ioAdapter.putString(outputString);

        String lastOutput = ioAdapter.pollOutput();

        assertThat(lastOutput).isEqualTo(outputString);
    }

    @Test
    void testConsoleMockingException(){
        IOAdapter ioAdapter = new ConsoleIOAdapter();

        assertThatThrownBy(() ->ioAdapter.queueInput("")).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(ioAdapter::pollOutput).isInstanceOf(RuntimeException.class);
    }
}
