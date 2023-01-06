package pl.first.project.book.store.controllers.rest.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.first.project.book.store.database.IUserDAO;
import pl.first.project.book.store.model.User;
import pl.first.project.book.store.model.dto.UserDTO;
import pl.first.project.book.store.model.dto.UsersResponse;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

    @Autowired
    @Qualifier("UserDatabase")
    IUserDAO userDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public UsersResponse getUsers() {
        UsersResponse usersResponse = new UsersResponse();
        for(User user : this.userDAO.getUsers()) {
            usersResponse.getUsers().add(new UserDTO(user));
        }
        return usersResponse;
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
        Optional<User> userBox = this.userDAO.getUserByLogin(login);
        if(userBox.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userBox.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        try {
            this.userDAO.addUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(user));
    }


}
