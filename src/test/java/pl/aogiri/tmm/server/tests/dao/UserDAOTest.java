package pl.aogiri.tmm.server.tests.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Database Access Object tests")
@ExtendWith({MockitoExtension.class})
public class UserDAOTest extends BaseDAOTest{

    static UserDAO userDAO;

    @BeforeAll
    static void setUp(@Autowired UserDAO userDAO){
        UserDAOTest.userDAO = userDAO;
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
    }

    @Test
    public void shouldNotFindUser(){
        Optional<UserEntity> actual = userDAO.findByLogin("noExist");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldCreateDisabledUser(){
        UserEntity actual = userDAO.save(UserEntity.builder()
                .login("disabledUser")
                .email("disabled@tmm.com")
                .password("Hash#d!1")
                .roles(Collections.emptyList())
                .enabled(true)
                .build());

        assertFalse(actual.getEnabled());
    }
}
