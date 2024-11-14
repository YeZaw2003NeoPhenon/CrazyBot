package security.login;

import java.util.Optional;

public interface UserLoginService {
    public abstract void UserCreditial();
    public abstract  void createAccount();

    public abstract Optional<UserAccount> getCurrentUserbyEmail(String email);

    public abstract  Optional<UserAccount> getCurrentUser();
    public abstract boolean deleteAccount(String email , String password);
    public abstract void deleteHelper();
}