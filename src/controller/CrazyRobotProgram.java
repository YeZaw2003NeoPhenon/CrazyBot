package controller;

import def.DefaultRequests;
import bot.CrazyBot;
import dictionary.DictionaryService;
import menu.UserMenus;
import security.User;
import security.login.UserAccount;
import security.login.UserLogin;
import security.UserService;

public class CrazyRobotProgram {
    private final UserService userService;
    private final DictionaryService dictionaryService;
    private final UserLogin userLogin;
    private final UserAccount account;
    CrazyRobotProgram(UserService userService, DictionaryService dictionaryService, UserLogin userLogin, UserAccount account) {
        this.userService = userService;
        this.dictionaryService = dictionaryService;
        this.userLogin = userLogin;
        this.account = account;

        userLogin.UserCreditial();

        User user = new User(dictionaryService);

        CrazyBot robot = new CrazyBot(dictionaryService,userLogin);

        UserMenus userMenus = new UserMenus(dictionaryService,userService,userLogin);

        userMenus.menu(user,robot);
    }

    public static void main(String[] args ) {
        UserLogin userLogin = new UserLogin();
        DictionaryService dictionaryService = new DictionaryService(userLogin);
        UserAccount account = new UserAccount();
        UserService userService = new UserService(dictionaryService,userLogin);
        new CrazyRobotProgram(userService,dictionaryService,userLogin,account);
    }

}