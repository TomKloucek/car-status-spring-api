package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;
import cz.cvut.fel.ear.carstatus.model.Admin;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.model.User;
import cz.cvut.fel.ear.carstatus.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = CarstatusApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
class UserDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    void findByUsernameReturnsPersonWithMatchingUsername() {
        final Admin admin = new Admin();
        final User user = new User();
        Random random = new Random();
        user.setUsername("admin");
        user.setFirstName("System");
        user.setBirthDate(new Date());
        user.setLastName("Administrator");
        user.setPassword("adm1n");
        user.setRole(Role.DRIVER);
        admin.setUser(user);

        em.persist(user);

        final User result = sut.findByUsername(user.getUsername());
        assertNotNull(result);
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getId(), result.getId());
    }


    @Test
    void findByUsernameReturnsNullForUnknownUsername() {
        assertNull(sut.findByUsername("unknownUsername"));
    }
}
