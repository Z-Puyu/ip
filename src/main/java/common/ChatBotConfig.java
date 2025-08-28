package common;

import comments.CommentContext;
import comments.CommentTopic;
import comments.Commenter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * A configuration for a chatbot. This allows different configurations for different bots.
 */
public class ChatBotConfig {
    private String name;
    private String logo;
    private String greeting;
    private String farewell;
    private Map<CommentTopic, Commenter> commenters;
    private Map<Type, String> errorMessages;

    private ChatBotConfig(String name, String logo, String greeting, String farewell, Map<CommentTopic, Commenter> commenters, Map<Type, String> errorMessages) {
        this.name = name;
        this.logo = logo;
        this.greeting = greeting;
        this.farewell = farewell;
        this.commenters = commenters;
        this.errorMessages = errorMessages;
    }

    public String getName() {
        return this.name;
    }

    public String getLogo() {
        return this.logo;
    }

    public String getGreeting() {
        return this.greeting;
    }

    public String getFarewell() {
        return this.farewell;
    }

    public String FetchComment(CommentTopic topic, CommentContext context) {
        Commenter commenter = commenters.getOrDefault(topic, null);
        if (commenter == null) {
            throw new IllegalArgumentException();
        }

        return commenter.commentOn(context);
    }

    public String FetchErrorMessage(RuntimeException e) {
        return this.errorMessages.getOrDefault(e.getClass(), "");
    }

    public static class Builder {
        private String name;
        private String logo;
        private String greeting;
        private String farewell;
        private Map<CommentTopic, Commenter> commenters = new HashMap<>();
        private Map<Type, String> errorMessages = new HashMap<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLogo(String logo) {
            this.logo = logo;
            return this;
        }

        public Builder withGreeting(String greeting) {
            this.greeting = greeting;
            return this;
        }

        public Builder withFarewell(String farewell) {
            this.farewell = farewell;
            return this;
        }

        public Builder withCommenter(CommentTopic topic, Commenter commenter) {
            this.commenters.put(topic, commenter);
            return this;
        }

        public Builder onError(Class<? extends RuntimeException> errorType, String msg) {
            this.errorMessages.put(errorType, msg);
            return this;
        }

        public ChatBotConfig build() {
            return new ChatBotConfig(this.name, this.logo, this.greeting, this.farewell, this.commenters, this.errorMessages);
        }
    }
}
