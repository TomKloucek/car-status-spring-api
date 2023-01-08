package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.model.Admin;
import cz.cvut.fel.ear.carstatus.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class SystemInitializer {

    private final cz.cvut.fel.ear.carstatus.log.Logger logger = new cz.cvut.fel.ear.carstatus.log.Logger();

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
        txTemplate.execute(status -> {
            generateAdmin();
            return null;
        });
        logger.log("System was successfully initialized.", ELoggerLevel.INFO);
    }

    /**
     * Generates an admin account if it does not already exist.
     */
    private void generateAdmin() {
        if (userService.exists(ADMIN_USERNAME)) {
            return;
        }
        final Admin admin = new Admin();
        admin.setUsername(ADMIN_USERNAME);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setBirthDate(new Date());
        admin.setPassword("adm1n");
        admin.setRole(Role.ADMIN);
        admin.setExpires(new Date());

        logger.log("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword() + admin.getRole(), ELoggerLevel.INFO);
        userService.persist(admin);
    }
}
