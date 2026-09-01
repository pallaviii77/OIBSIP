import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();

        // Sample accounts
        accounts.add(new Account("1001", 1234, 10000));
        accounts.add(new Account("1002", 5678, 5000));
        accounts.add(new Account("1003", 1111, 15000));
    }

    public Account findAccount(String userId) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)) {
                return account;
            }
        }

        return null;
    }

    public boolean addAccount(Account account) {

        if (findAccount(account.getUserId()) != null) {
            return false;
        }

        accounts.add(account);
        return true;
    }
}