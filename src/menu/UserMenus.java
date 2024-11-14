package menu;

import bot.CrazyBot;
import dictionary.Dictionary;
import dictionary.DictionaryService;
import request.UserInput;
import security.User;
import security.login.UserLogin;
import security.UserService;

import java.util.List;

public class UserMenus {

   private final DictionaryService dictionaryService;
   private final UserService userService;

   private final UserLogin userLogin;

   public UserMenus(DictionaryService dictionaryService, UserService userService, UserLogin userLogin){
       this.dictionaryService = dictionaryService;
       this.userService = userService;
       this.userLogin = userLogin;
   }
    public void menu(User user , CrazyBot robot) {
        UserInput.getInstance().printMessage("1. Veg out CrazyRobot Operations");
        UserInput.getInstance().printMessage("2. Get All Questions and Responses");
        UserInput.getInstance().printMessage("3. View Bot Responses");
        UserInput.getInstance().printMessage("4. Search Response by any keyword");
        UserInput.getInstance().printMessage("5. Delete User Account");
        UserInput.getInstance().printMessage("6. View All Questions");
        UserInput.getInstance().printMessage("7. Update Response");
        String input = UserInput.getInstance().getInput("You can now pick out any operation you wanna live up on");

        while(true){

            if( input.equals("exit") || input.equals(String.valueOf(0))) {
                System.exit(0);
                break;
            }

            switch (input) {
                case "1":
                    userService.mainOperation(user, robot);
                    break;

                case "2":
                    List<String> questionsAndResponses = dictionaryService.getAllQuestionsAndResponses();
                    if (questionsAndResponses.isEmpty()) {
                        UserInput.getInstance().printMessage("No questions and responses stored yet.");
                    }
                    questionsAndResponses.forEach(UserInput.getInstance()::printMessage);
                    break;

                case "3":
                    dictionaryService.viewBotResponses();
                    break;

                case "4":
                    String qs = UserInput.getInstance().getInput("Search response by printing any keyword :");
                    String response = dictionaryService.searchByKeyword(qs);
                    UserInput.getInstance().printMessage(response == null ? "No Responses trackable" : response);
                    break;

                case "5":
                    userLogin.deleteHelper();
                    break;

                case "6":
                  dictionaryService.viewAllQuestions();
                    break;
                case "7":
                    dictionaryService.update();
                    break;
                default:
                    UserInput.getInstance().printMessage("Something Wrong, try again!");
                    UserInput.getInstance().close();
                    break;
            }
            input = UserInput.getInstance().getInput("Choose an operation: ");
        }
    }

}
