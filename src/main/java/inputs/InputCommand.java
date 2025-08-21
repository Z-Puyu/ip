package inputs;

import java.util.StringTokenizer;
import java.util.function.Function;

public record InputCommand(InputAction action, String text, StringTokenizer args) {
    public <T> T nextArg(Function<String, T> parser) {
        if (!args.hasMoreTokens()) {
            throw new IllegalArgumentException(String.format("Command %s is used with wrong arguments", this.text));
        }

        return parser.apply(args.nextToken());
    }
}
