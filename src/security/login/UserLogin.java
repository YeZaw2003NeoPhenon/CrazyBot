package security.login;

import def.DefaultRequests;
import request.UserInput;
import security.login.UserAccount;
import security.login.UserLoginService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserLogin implements UserLoginService {
    private final Map<String, UserAccount> login_map ; // key => email , val => relentless attributes from account
    private final Map<String,String> welcome_map;

    private final Map<String,UserAccount> accountMap;

    private String currentUserEmail; // local variable to access user for other class

    boolean createAccount = false;

    public UserLogin(){
        this.login_map = new HashMap<>();
        this.welcome_map = new HashMap<>();
        this.accountMap = new HashMap<>();
    }

    @Override
    public void UserCreditial() {
        initializeDefaults();
        String input = UserInput.getInstance().getInput(" Do you have an account? ( yes (or) no )");
        if(input.equalsIgnoreCase("no") && !createAccount){
            createAccount();
            createAccount = true;
        }

//        welcome_map.put(DefaultRequests.user_email,User_name);

        boolean isLogged_in = false;

        while(!isLogged_in){

            String input_email = UserInput.getInstance().getInput("Please Enter the Email");

            String input_password = UserInput.getInstance().getInput(" Please Enter the password");

            if(createAccount){
                dataTransfer();
            }

            UserAccount account = login_map.get(input_email);

            if( account != null && login_map.containsKey(input_email) && account.getPassword().equalsIgnoreCase(input_password)) {
                isLogged_in = true;
                currentUserEmail = input_email;
                UserInput.getInstance().printMessage("Login successful!");
                System.out.println("-------------------");

                if(welcome_map.containsKey(input_email)) {
                    UserInput.getInstance().printMessage(" Welcome : " + welcome_map.get(input_email)+"!"+" To Mesmerizing CrazyBot System.");
                    UserInput.getInstance().printMessage(" ---------------- ");
                    System.out.println();
                }
            }
            else {
                UserInput.getInstance().printMessage("Erroenous email and password . Just Try Again ");
                UserInput.getInstance().printMessage("-------------------");
                isLogged_in = false;
            }
        }
    }

    private void dataTransfer() {
        // clear out datas from current login map to avoid any debilitating duplication
        login_map.clear();
        accountMap.entrySet().stream().forEach( entry -> login_map.put(entry.getKey(), entry.getValue()));
    }
    @Override
    public void createAccount() {
        String email = UserInput.getInstance().getInput("Enter a new email: ");
        String password = UserInput.getInstance().getInput("Enter a password: ");
        String name = UserInput.getInstance().getInput("Enter your name: ");

        if(!accountMap.containsKey(email)){
            UserAccount newAccount = new UserAccount(email,password,name);
            accountMap.put(email,newAccount);
            welcome_map.put(email,name);
            UserInput.getInstance().printMessage("Account created successfully! Please log in.");
        }
        else{
            UserInput.getInstance().printMessage("An account with that email already exists.");
        }
    }

    private void initializeDefaults() {
        login_map.put(DefaultRequests.user_email, new UserAccount( DefaultRequests.user_email , DefaultRequests.user_password , "User"));
        welcome_map.put(DefaultRequests.user_email , "Neo Courney");
    }

    @Override
    public Optional<UserAccount> getCurrentUserbyEmail(String email) {
        return Optional.ofNullable(login_map.get(email));
    }

    @Override
    public Optional<UserAccount> getCurrentUser() {
        return Optional.ofNullable(login_map.get(currentUserEmail));
    }
    @Override
    public boolean deleteAccount(String email, String password) {
     UserAccount userAccount =  getCurrentUserbyEmail(email).orElse(null);

         if( userAccount != null && userAccount.getEmail().equalsIgnoreCase(email) &&
            userAccount.getPassword().equalsIgnoreCase(password)){
             login_map.remove(email);
             accountMap.remove(email);
             welcome_map.remove(email);
             return true;
         }
             return false;
    }
    @Override
    public void deleteHelper() {
        String email = UserInput.getInstance().getInput("Enter your email to delete account: ");
        String password = UserInput.getInstance().getInput("Enter your password: ");

        boolean isDeleted = deleteAccount(email , password);

        if(isDeleted){
            UserInput.getInstance().printMessage("Account deleted successfully.");
            UserCreditial();
        }
        else{
            UserInput.getInstance().printMessage("Incorrect password. Account deletion failed.");
        }
    }
}
