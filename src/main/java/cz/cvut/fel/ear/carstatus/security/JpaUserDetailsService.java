package cz.cvut.fel.ear.carstatus.security;

import cz.cvut.fel.ear.carstatus.dao.UserDao;
import cz.cvut.fel.ear.carstatus.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    public JpaUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(userDao.findByUsername(username));
    }
}
