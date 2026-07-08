import java.io.InputStream;
import java.io.PrintStream;

// Feeds a fixed set of answers into the console and echoes each line to the
// screen as it is read. That way a recorded run looks like someone typing at the
// prompts, which is what I need for the screenshots. It hands back one line at a
// time so the echo lines up with the prompt that asked for it.
public class K2521133ScriptedInput extends InputStream {
    private final byte[] data;
    private final PrintStream echo;
    private int pos = 0;

    public K2521133ScriptedInput(String script, PrintStream echo) {
        this.data = script.getBytes();
        this.echo = echo;
    }

    @Override
    public int read() {
        if (pos >= data.length) {
            return -1;
        }
        int b = data[pos++] & 0xFF;
        echo.write(b);
        echo.flush();
        return b;
    }

    @Override
    public int read(byte[] buffer, int off, int len) {
        if (pos >= data.length) {
            return -1;
        }
        int count = 0;
        while (count < len && pos < data.length) {
            byte ch = data[pos++];
            buffer[off + count++] = ch;
            echo.write(ch);
            if (ch == '\n') {
                break;
            }
        }
        echo.flush();
        return count;
    }
}
