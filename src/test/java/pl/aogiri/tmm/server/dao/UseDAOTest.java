package pl.aogiri.tmm.server.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.utils.ReplaceCamelCase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Database Access Object tests")
@DisplayNameGeneration(ReplaceCamelCase.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UseDAOTest {

    static UserDAO userDAO;

    @BeforeAll
    static void setUp(@Autowired UserDAO userDAO){
        UseDAOTest.userDAO = userDAO;
        userDAO.save(UserEntity.builder().login("exist").email("exist@tmm.com").password("Has$ed#").id(1L).enabled(true).build());
    }

    @Test
    public void shouldCountByEmailZero(){
        Long actual = userDAO.countByEmail("noExist@email.com");
        assertEquals(0, actual);
    }

    @Test
    public void shouldCountByEmailOne(){
        Long actual = userDAO.countByEmail("exist@tmm.com");
        assertEquals(1, actual);
    }

    @Test
    public void shouldCountByLoginZero(){
        Long actual = userDAO.countByLogin("noExist");
        assertEquals(0, actual);
    }

    @Test
    public void shouldCountByLoginOne(){
        Long actual = userDAO.countByLogin("exist");
        assertEquals(1, actual);
    }


    @Test
    public void shouldFindUser(){
        Optional<UserEntity> actual = userDAO.findByLogin("exist");
        assertFalse(actual.isEmpty());
        assertEquals(1L, actual.get().getId());
    }

    @Test
    public void shouldNotFindUser(){
        Optional<UserEntity> actual = userDAO.findByLogin("noExist");
        assertTrue(actual.isEmpty());
    }
}
