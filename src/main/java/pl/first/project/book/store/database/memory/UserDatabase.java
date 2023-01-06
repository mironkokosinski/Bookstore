package pl.first.project.book.store.database.memory;

import org.springframework.stereotype.Component;
import pl.first.project.book.store.database.IUserDAO;
import pl.first.project.book.store.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("UserDatabase")
public class UserDatabase implements IUserDAO {
    private List<User> users = new ArrayList<>();

    public UserDatabase() {
        users.add(new User(1, "admin", "3e3e6b0e5c1c68644fc5ce3cf060211d"));
        users.add(new User( 2, "miron", "087d9c5e13bdd64a82bef8e013625c32"));
    }

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> getUserByLogin(String login) {
        for (User user : this.users) {
            if(user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean isLoginExist(String login) {
        return getUserByLogin(login).isPresent();
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
