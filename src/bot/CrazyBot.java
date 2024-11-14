package bot;

import dictionary.DictionaryService;
import security.User;
import security.login.UserAccount;
import security.login.UserLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrazyBot extends User {
    private Map<String,String> robot_responses = new HashMap<>();

    private final DictionaryService dictionaryService;

    private final UserLogin userLogin ;

    public CrazyBot(DictionaryService dictionaryService,UserLogin userLogin){
        super(dictionaryService);
        this.dictionaryService = dictionaryService;
        this.userLogin = userLogin;
        initializeResponses();
    }

    public void initializeResponses() {
            Optional<UserAccount> currUser = userLogin.getCurrentUser();
            if (currUser.isPresent()) {
                robot_responses.put("greeting", "Hello, " + currUser.get().getName() + "! I am Crazy Bot \n");
            } else {
                robot_responses.put("greeting", "Hello! I am Crazy Bot \n");
            }
    }

    public void SayThankYou() {
        System.out.println(" Thanks for teaching me. \n ");
    }

    @Override
    public String respond( String question ) {
        String response = dictionaryService.searchResponseByQuestion(question);

        if (response != null) {
            return response;
        }
        else if(question.equals("Hi") || question.equals("Hello CrazyBot")) {
            return robot_responses.get("greeting");
        }
        else {
            return robot_responses.get("unknown");
        }
    }
}