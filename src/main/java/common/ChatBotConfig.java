package common;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import comments.CommentContext;
import comments.CommentTopic;
import comments.Commenter;

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

    private ChatBotConfig(
            String name, String logo, String greeting, String farewell,
            Map<CommentTopic, Commenter> commenters, Map<Type, String> errorMessages
    ) {
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

    /**
     * Fetches a comment based on the topic and context.
     * @param topic the topic of the comment
     * @param context the context of the comment
     * @return the comment
     */
    public String fetchComment(CommentTopic topic, CommentContext context) {
        Commenter commenter = commenters.getOrDefault(topic, null);
        if (commenter == null) {
            throw new IllegalArgumentException();
        }

        return commenter.commentOn(context);
    }

    public String fetchErrorMessage(RuntimeException e) {
        return this.errorMessages.getOrDefault(e.getClass(), "");
    }

    /**
     * A builder for ChatBotConfig.
     */
    public static class Builder {
        private String name;
        private String logo;
        private String greeting;
        private String farewell;
        private Map<CommentTopic, Commenter> commenters = new HashMap<>();
        private Map<Type, String> errorMessages = new HashMap<>();

        /**
         * Sets the name of the chatbot.
         * @param name the name of the chatbot
         * @return this builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the logo of the chatbot.
         * @param logo the logo of the chatbot
         * @return this builder
         */
        public Builder withLogo(String logo) {
            this.logo = logo;
            return this;
        }

        /**
         * Sets the greeting text for the config.
         * @param greeting the greeting text
         * @return this builder
         */
        public Builder withGreeting(String greeting) {
            this.greeting = greeting;
            return this;
        }

        /**
         * Sets the farewell text for the config.
         * @param farewell the farewell text
         * @return this builder
         */
        public Builder withFarewell(String farewell) {
            this.farewell = farewell;
            return this;
        }

        /**
         * Sets the commenter for a specific topic.
         * @param topic the topic
         * @param commenter the commenter
         * @return this builder
         */
        public Builder withCommenter(CommentTopic topic, Commenter commenter) {
            this.commenters.put(topic, commenter);
            return this;
        }

        /**
         * Sets the error message for a specific error type.
         * @param errorType the error type
         * @param msg the error message
         * @return this builder
         */
        public Builder onError(Class<? extends RuntimeException> errorType, String msg) {
            this.errorMessages.put(errorType, msg);
            return this;
        }

        /**
         * Builds the chatbot config.
         * @return the chatbot config
         */
        public ChatBotConfig build() {
            return new ChatBotConfig(
                    this.name, this.logo, this.greeting, this.farewell, this.commenters, this.errorMessages
            );
        }
    }
}
