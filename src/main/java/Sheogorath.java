import java.util.Scanner;

public class Sheogorath {
    public static void main(String[] args) {
        InputMapping.getInstance().map("bye", InputAction.Quit);
        Application.fetchInstance().boot();
    }
}
