package request;

import java.util.Scanner;

public class UserInput {
    private static UserInput instance;

    private final Scanner scanner;
    private UserInput(){
        this.scanner = new Scanner(System.in);
    }
    public static UserInput getInstance() {
        if( instance == null ){
            instance = new UserInput();
        }
        return instance;
    }
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    public void printMessage(String message) {
        System.out.println(message);
    }
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
