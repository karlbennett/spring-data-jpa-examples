package org.springframework.data.jpa.example.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.example.domain.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


/**
 * Integration test showing the working global load customisation.
 *
 * @author Karl Bennett
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository-context.xml")
public class LoadAwareRepositorySample {

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() throws Exception {

        user = new User();
        user.setUsername("username");
        user.setFirstname("firstname");
        user.setLastname("lastname");

        repository.save(user);
    }

    @After
    public void tearDown() throws Exception {

        repository.delete(user);
    }


    /**
     * Test loading an existing user instance with persisted data.
     */
    @Test
    public void testUserLoad() {

        User loadedUser = new User();

        repository.load(loadedUser, user.getId());

        assertEquals(user.getId(), loadedUser.getId());
        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(user.getFirstname(), loadedUser.getFirstname());
        assertEquals(user.getLastname(), loadedUser.getLastname());
    }
}
