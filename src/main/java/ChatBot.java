public class ChatBot {
    private String name;

    public ChatBot(String name) {
        this.name = name;
    }

    public void GreetUser() {
        String greeting = """
                Hey, you! Finally awake!
                You know me. You just don't know it.
                Sheogorath, Daedric Prince of Madness. At your service.
                """;
        System.out.println(greeting);
    }

    public void SayGoodbye() {
        System.out.println("Well, I suppose it's back to the Shivering Isles.");
    }

    @Override
    public String toString() {
        return name;
    }
}
