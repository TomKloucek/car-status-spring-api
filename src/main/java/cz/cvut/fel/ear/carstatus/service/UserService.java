package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.model.User;
import cz.cvut.fel.ear.carstatus.util.Constants;
import cz.cvut.fel.ear.carstatus.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;
    private final PasswordEncoder encoder;

    final User currentUser = new User();

    @Autowired
    public UserService(UserDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }
        DataClass.getInstance().incrementNumberOfUsersRegistered();
        dao.persist(user);
    }


    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }
}


