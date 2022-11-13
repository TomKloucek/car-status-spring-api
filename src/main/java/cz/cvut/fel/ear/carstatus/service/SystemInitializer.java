package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.model.Admin;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * Default admin username
     */
    private static final String ADMIN_USERNAME = "carstatus-admin@fel.ear.cvut.cz";

    private final UserService userService;

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(UserService userService,
                             PlatformTransactionManager txManager) {
        this.userService = userService;
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            generateAdmin();
            return null;
        });
    }

    /**
     * Generates an admin account if it does not already exist.
     */
    private void generateAdmin() {
        if (userService.exists(ADMIN_USERNAME)) {
            return;
        }
        final Admin admin = new Admin();



        User user = new User();
        user.setUsername(ADMIN_USERNAME);
        user.setFirstName("System");
        user.setBirthDate(new Date());
        user.setLastName("Administrator");
        user.setPassword("adm1n");
        user.setRole(Role.ADMIN);
        admin.setUser(user);
        LOG.info("Generated admin user with credentials " + user.getUsername() + "/" + user.getPassword());
        userService.persist(user);
    }
}
