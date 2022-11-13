package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.model.User;
import cz.cvut.fel.ear.carstatus.util.Constants;
import cz.cvut.fel.ear.carstatus.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    final User currentUser = new User();

    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }
        dao.persist(user);
    }


    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }
}


