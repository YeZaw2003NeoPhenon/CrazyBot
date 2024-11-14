package dictionary;

import request.UserInput;
import security.login.UserLogin;

import java.util.*;
import java.util.stream.Collectors;

public class DictionaryService implements DictionaryRepo{

    //    public String questions[]; // List<String> questions_list = new ArrayList<>();
    //    public String responses[]; // List<String> responseList = new ArrayList<>();
    private final Map<String, Dictionary> questionResponseMap; // key -> question , val -> dictionary obj which contains response

    private final UserLogin userLogin;
    // to impeccably store the indispensable questions and responses that the user put out ;
    // As a question and response Structure
    public DictionaryService(UserLogin userLogin){
        this.userLogin = userLogin;
//        this.questions = new String[100];
//        this.responses = new String[100];
        this.questionResponseMap = new HashMap<>();
    }

    @Override
    public void Robot_learning_process(String question , String response ) {
        questionResponseMap.merge(question , new Dictionary(question , response) , (existing, newEntry) -> {
                    String combinedResponses = existing.getResponse() + "," + newEntry.getResponse();
                    existing.setResponse(combinedResponses);
                    return existing;
                });
    }

    @Override
    public String searchResponseByQuestion(String question) {

        // return response
        return questionResponseMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(question))
                .findFirst()
                .map(dictionary -> dictionary.getValue().getResponse())
                .orElse(null);
    }

    @Override
    public List<String> getAllQuestionsAndResponses() {
        return questionResponseMap.entrySet().stream()
                .map(entry -> "Question" + "("+userLogin.getCurrentUser().get().getName()+")" + " => "+ entry.getKey() + "\n" + "Response(Robot) => " + entry.getValue().getResponse())
                .collect(Collectors.toList());
    }
    @Override
    public List<String> viewBotResponses() {

        List<String> responses =  questionResponseMap.values()
                .stream()
                .map(Dictionary::getResponse)
                .distinct()
                .limit(20)
                .collect(Collectors.toList());

        if(responses.isEmpty()) {
            UserInput.getInstance().printMessage("No bot responses available yet.");
            return Collections.emptyList();
        }

        responses.forEach(UserInput.getInstance()::printMessage);
        return responses;
    }
    @Override
    public void viewAllQuestions() {
        if( questionResponseMap.isEmpty()){
            UserInput.getInstance().printMessage(Collections.emptyList().toString());
        }

         List<String> qs =  new ArrayList<>(questionResponseMap.keySet());

         qs.forEach(UserInput.getInstance()::printMessage);

        // questionResponseMap.isEmpty();
    }

    @Override
    public String searchByKeyword(String keyword) {

        return questionResponseMap.keySet()
                .stream()
                .filter(question -> question.toLowerCase().contains(keyword.toLowerCase()))
//                .map(question -> question.isBlank() ? "No question" : question)
                 .collect(Collectors.joining(", "));
    }
    @Override
    public void clearAll() {
        questionResponseMap.clear();
    }

    @Override
    public boolean updateResponse(String question , String newResponse){
//        boolean[] isUpdated = {false};
    return questionResponseMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(question))
                .findFirst()
                .map(entry -> {
                    entry.setValue(new Dictionary(entry.getKey() , newResponse));
                    return true;
                }).orElse(false);
    }

    @Override
    public void update() {
     String inputQuestion = UserInput.getInstance().getInput("Enter the question to update");
     String newResponse = UserInput.getInstance().getInput("Enter the new response:");

     boolean update = updateResponse(inputQuestion,newResponse);

     if(update){
         UserInput.getInstance().printMessage("Response updated successfully!");
     }
     else{
         UserInput.getInstance().printMessage("Question not found. Unable to update.");
     }
    }

}
