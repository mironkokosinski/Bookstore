package pl.first.project.book.store.services.impl;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.first.project.book.store.database.IEntitySever;
import pl.first.project.book.store.database.IUserDAO;
import pl.first.project.book.store.exceptions.LoginAlreadyExistException;
import pl.first.project.book.store.model.User;
import pl.first.project.book.store.services.IAuthenticationService;
import pl.first.project.book.store.session.SessionObject;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    @Qualifier("UserDatabase")
    IUserDAO userDAO;

    @Autowired
    IEntitySever entitySever;

    @Resource
    SessionObject sessionObject;

    public void authenticate(User user) {
        Optional<User> userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if(userFromDatabase.isPresent() &&
                userFromDatabase.get().getPassword().equals(DigestUtils.md2Hex(user.getPassword()))) {
            this.sessionObject.setUser(userFromDatabase.get());
        }
    }

    public void register(User user) {
        if (this.userDAO.isLoginExist(user.getLogin())) {
            throw new LoginAlreadyExistException();
        }
        user.setPassword(DigestUtils.md2Hex(user.getPassword()));
        this.entitySever.persistEntity(user);
    }

    public void logout() {
        this.sessionObject.setUser(null);
    }

    @Override
    public void addCommonInfoToModel(Model model) {
        model.addAttribute("logged", this.sessionObject.isLogged());
        model.addAttribute("user", this.sessionObject.getUser());
    }
}
