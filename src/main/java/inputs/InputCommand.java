package inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

public record InputCommand(InputAction action, String text, StringTokenizer args) {
    public <T> T nextArg(Function<String, T> parser) {
        String arg = this.readUntil(' ');
        if (arg.isBlank()) {
            throw new IllegalArgumentException(String.format("Command %s is used with wrong arguments", this.text));
        }

        return parser.apply(arg);
    }

    public String nextArg() {
        return this.readUntil(' ');
    }

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
