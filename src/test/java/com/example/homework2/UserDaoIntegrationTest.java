package com.example.homework2;

import com.example.homework2.config.HibernateConfig;
import com.example.homework2.dao.api.UserDao;
import com.example.homework2.dao.impl.UserDaoImpl;
import com.example.homework2.model.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoIntegrationTest {
    @Container
    private static final GenericContainer<?> postgres =
            new GenericContainer<>(DockerImageName.parse("postgres:15-alpine"))
                    .withEnv("POSTGRES_DB", "testdb")
                    .withEnv("POSTGRES_USER", "test")
                    .withEnv("POSTGRES_PASSWORD", "test")
                    .withExposedPorts(5432);
    private SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeAll
    public void setup(){
        postgres.start();

        String jdbcUrl = "jdbc:postgresql://" + postgres.getHost() + ":" + postgres.getMappedPort(5432) + "/testdb";

        HibernateConfig.init(jdbcUrl, "test", "test", "create-drop");
        sessionFactory = HibernateConfig.getSessionFactory();
        userDao = new UserDaoImpl();
    }

    @AfterAll
    public void shutdown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
        postgres.stop();
    }

    @Test
    public void testCreateAndReadUser(){
        User user = new User("1", "2", 3);
        userDao.save(user);

        List<User> userFromDb = userDao.findAllByUsername("1");
        assertNotNull(userFromDb);
        assertEquals("1", userFromDb.get(0).getName());
        userDao.delete(user);
    }

    @Test
    public void testUpdate(){
        User user = new User("1", "2", 3);
        userDao.save(user);

        user.setName("2");
        userDao.update(user);

        List<User> userFromDb = userDao.findAllByUsername("2");
        assertNotNull(userFromDb);
        assertEquals("2", userFromDb.get(0).getName());
        userDao.delete(userFromDb.get(0));
    }

    @Test
    public void testDelete(){
        User user = new User("1", "2", 3);
        userDao.save(user);

        userDao.delete(user);
        List<User> deleted = userDao.findAllByUsername("1");

        assertEquals(0, deleted.size());
    }


}
