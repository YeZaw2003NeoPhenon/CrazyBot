package dictionary;

import dictionary.DictionaryRepo;
import request.UserInput;
import security.login.UserLogin;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionary  {

    private String question;
    private String response;

    public Dictionary(String question, String response) {
        this.question = question;
        this.response = response;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}