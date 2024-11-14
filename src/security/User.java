package security;

import dictionary.DictionaryService;
import security.login.UserAccount;

public class User {

    String name ;
    protected DictionaryService dictionaryService;

    private UserAccount userAccount;
     String defaultResponse;

    public User(DictionaryService dictionaryService, String defaultResponse){
        this.name = userAccount.getName() ;
        this.dictionaryService= dictionaryService ;
        this.defaultResponse = defaultResponse;
    }
    User(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public User(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void Userinstruction(String question , String response ) {
        dictionaryService.Robot_learning_process(question, response);
    }

    public String respond( String question ) {
        String userResponse = dictionaryService.searchResponseByQuestion(question);

       return userResponse != null ? userResponse : defaultResponse;
    }

}
