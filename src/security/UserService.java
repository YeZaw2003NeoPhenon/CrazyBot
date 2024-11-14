package security;
import bot.CrazyBot;
import dictionary.DictionaryService;
import menu.UserMenus;
import request.UserInput;
import security.login.UserAccount;
import security.login.UserLogin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserService {

    private final DictionaryService dictionaryService;
    private final UserLogin userLogin;

    private Set<String> userSet = new HashSet<>(); // O(1) quick-lookup
    public UserService(DictionaryService dictionaryService, UserLogin userLogin){ this.dictionaryService = dictionaryService; this.userLogin = userLogin;}

    public void mainOperation(User user , CrazyBot robot ){

        String previousUser_question = null;

        rememberUserName();
        while(true){
            String user_question = UserInput.getInstance().getInput("User: ");

            if( user_question.equals("exit") || user_question.equals(String.valueOf(0))) {
                System.exit(0);
                break;
            }

            String bot_response = robot.respond(user_question);
            if(bot_response == null){
                UserInput.getInstance().printMessage("Sorry, I don't understand that.");
                continue;
            }
            UserInput.getInstance().printMessage("CrazyBot: " + bot_response);

            if (previousUser_question != null && !previousUser_question.equalsIgnoreCase(user_question)) {
                handleUser_response(robot,user_question,previousUser_question);
                previousUser_question = "";
            } else {
                if (!bot_response.equals(user.defaultResponse)) {
                    askForRobotFeedback(robot,user_question);
                } else {
                    previousUser_question = user_question;
                }
            }

            String ans = UserInput.getInstance().getInput("Return to the main menu? (Yes/No): ");
            if( ans.equalsIgnoreCase("Yes")){
                new UserMenus(dictionaryService , this , userLogin).menu(user,robot);
            }

        }
        UserInput.getInstance().close();
    }
    private void rememberUserName() {
       Optional<UserAccount> user = userLogin.getCurrentUser();
        if(userSet.contains(user.get().getName())){
            UserInput.getInstance().printMessage("Welcome back, " + user.get().getName()+ "!");
        }
        else{
            UserInput.getInstance().printMessage("Nice to meet you, " + user.get().getName() + "!");
            userSet.add(user.get().getName());
        }
    }

    public void askForRobotFeedback(CrazyBot robot, String user_question) {
        UserInput.getInstance().printMessage(" How should I respond ligitimately to " + " >>" + user_question + "<<");
        String userResponse = UserInput.getInstance().getInput("\n Please provide a response:");

        if (!userResponse.isEmpty()) {
            this.handleUser_response(robot, user_question, userResponse);
        }
    }

    // I lashed up precisely 2 hours to wrench out of this multifarious logic
    public void handleUser_response(CrazyBot robot, String user_question, String userResponse) {
        robot.Userinstruction(user_question, userResponse); // question -> response
        robot.SayThankYou();
    }

}
