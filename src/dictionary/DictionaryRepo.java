package dictionary;

import java.util.List;

public interface DictionaryRepo {
    public abstract void Robot_learning_process(String question , String response);
    public abstract String searchResponseByQuestion(String question);
    public abstract List<String> getAllQuestionsAndResponses();
    public abstract List<String> viewBotResponses();

    public abstract void viewAllQuestions();
    public abstract String searchByKeyword(String keyword);

    public abstract void clearAll();

    public abstract boolean updateResponse(String question , String response);

    public abstract void update();
}