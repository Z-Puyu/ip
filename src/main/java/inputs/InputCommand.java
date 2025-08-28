package inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

/**
 * A command that can be interpreted by the application.
 */
public record InputCommand(InputAction action, String text, StringTokenizer args) {
    /**
     * Parses the next argument as a specific type.
     * @param parser the parser function
     * @return the parsed argument
     * @param <T> the type of the parsed argument
     */
    public <T> T nextArg(Function<String, T> parser) {
        String arg = this.readUntil(' ');
        if (arg.isBlank()) {
            throw new IllegalArgumentException(String.format("Command %s is used with wrong arguments", this.text));
        }

        return parser.apply(arg);
    }

    /**
     * Reads the next argument.
     * @return the next argument
     */
    public String nextArg() {
        return this.readUntil(' ');
    }

    /**
     * Reads the next argument until the given delimiter.
     * @param delimiter the delimiter to stop reading at
     * @return the next argument
     */
    public String readUntil(char delimiter) {
        List<String> tokens = new ArrayList<>();
        while (this.args.hasMoreTokens()) {
            String token = this.args.nextToken();
            if (token.charAt(0) == delimiter) {
                break;
            }

            tokens.add(token);
        }

        return String.join(" ", tokens);
    }

    /**
     * Reads the next argument until the given delimiter.
     * @param delimiter the delimiter to stop reading at
     * @return the next argument
     */
    public String readUntil(String delimiter) {
        List<String> tokens = new ArrayList<>();
        while (this.args.hasMoreTokens()) {
            String token = this.args.nextToken();
            if (token.startsWith(delimiter)) {
                break;
            }

            tokens.add(token);
        }

        return String.join(" ", tokens);
    }

    /**
     * Reads the next argument until any of the given delimiters.
     * @param delimiters the delimiters to stop reading at
     * @return the next argument
     */
    public String readUntil(String... delimiters) {
        List<String> tokens = new ArrayList<>();
        while (this.args.hasMoreTokens()) {
            String token = this.args.nextToken();
            if (Arrays.stream(delimiters).anyMatch(token::startsWith)) {
                break;
            }

            tokens.add(token);
        }

        return String.join(" ", tokens);
    }
}
