package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.model.Admin;
import cz.cvut.fel.ear.carstatus.model.Driver;
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
            final Driver driver = new Driver();
            driver.setUsername("driver1@fel.ear.cvut.cz");
            driver.setFirstName("Albert");
            driver.setLastName("Fast");
            driver.setBirthDate(new Date());
            driver.setPassword("albert");
            driver.setRole(Role.DRIVER);
            userService.persist(driver);
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

        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword() + admin.getRole());
        userService.persist(admin);

    }
}
