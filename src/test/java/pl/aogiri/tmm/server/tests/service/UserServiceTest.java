package pl.aogiri.tmm.server.tests.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.service.implementation.UserService;
import pl.aogiri.tmm.server.utils.ReplaceCamelCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("User Service")
@DisplayNameGeneration(value = ReplaceCamelCase.class)
@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    private static UserDAO userDAO;

    @InjectMocks
    private static UserService userService;

    @BeforeAll
    public static void setUp() {
        userService = new UserService(userDAO);
    }

    @Test
    public void shouldFindUserById() {
        final Long PRE_ID = 1L;
        final UserEntity PRE_USER = UserEntity.builder().id(PRE_ID).login("root").email("root@tmm.com").enabled(true).password("123").roles(Collections.emptyList()).build();
        when(userDAO.findById(PRE_ID)).thenReturn(Optional.of(PRE_USER));

        Optional<UserDTO> user = userService.findById(PRE_ID);
        assertFalse(user.isEmpty());
        assertEquals(PRE_ID, user.get().getId());
        assertEquals(PRE_USER.toDTO(), user.get());
    }

    @Test
    void shouldNotFindUserById() {
        when(userService.findById(anyLong())).thenReturn(Optional.empty());

        Optional<UserDTO> user = userService.findById(1L);
        assertTrue(user.isEmpty());
    }

    @Test
    void shouldFindAllUsers() {
        Iterable<UserEntity> userEntities = Arrays.asList(
                UserEntity.builder().id(1L).login("root").email("root@tmm.com").enabled(true).password("123").roles(Collections.emptyList()).build(),
                UserEntity.builder().id(2L).login("user").email("user@tmm.com").enabled(true).password("456").roles(Collections.emptyList()).build()
        );
        when(userDAO.findAll()).thenReturn(userEntities);

        Collection<UserDTO> usersActual = userService.findAll();
        assertFalse(usersActual.isEmpty());
        assertEquals(2, usersActual.size());
    }

    @Test
    void shouldReturnEmptyList() {
        when(userDAO.findAll()).thenReturn(Collections.emptyList());

        Collection<UserDTO> usersActual = userService.findAll();
        assertTrue(usersActual.isEmpty());
    }

}
