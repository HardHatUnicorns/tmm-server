package pl.aogiri.tmm.server.tests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.service.implementation.UserService;
import pl.aogiri.tmm.server.util.PasswordEncoder;
import pl.aogiri.tmm.server.utils.ReplaceCamelCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("User Service")
@DisplayNameGeneration(value = ReplaceCamelCase.class)
@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    private static UserDAO userDAO;

    @Mock
    private static PasswordEncoder encoder;

    private static UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDAO, encoder);
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

    @Test
    void shouldCreateNewUser() {
        UserEntity userExpected = UserEntity.builder().id(2L).login("user").email("user@tmm.com").enabled(false).roles(Collections.emptyList()).build();
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("456");
        when(userDAO.save(any(UserEntity.class))).then(ans -> {
            UserEntity tmp = ans.getArgument(0, UserEntity.class);
            tmp.setId(2L);
            tmp.setRoles(Collections.emptyList());
            tmp.setEnabled(false);
            return tmp;
        });

        Optional<UserDTO> userActual = userService.createUser(userFromUi);
        assertFalse(userActual.isEmpty());
        UserDTO userActualDTO = userActual.get();
        assertEquals(userExpected.toDTO(), userActualDTO);
    }

    @Test
    void shouldReturnResponseAboutLoginExist() {
        UserDTO userToAdd = UserEntity.builder().login("user").email("user@tmm.com").password("456").roles(Collections.emptyList()).build().toDTO();
        userToAdd.setPlainPassword("123");
        when(userDAO.countByLogin(anyString())).thenReturn(1L);
        when(encoder.encode(any())).thenReturn("Pa$$w0rd");

        assertThrows(LoginExistException.class, () -> userService.createUser(userToAdd));
    }

    @Test
    void shouldReturnResponseAboutEmailExist() {
        UserDTO userToAdd = UserEntity.builder().login("user").email("user@tmm.com").password("456").roles(Collections.emptyList()).build().toDTO();
        userToAdd.setPlainPassword("123");
        when(userDAO.countByEmail(anyString())).thenReturn(1L);
        when(encoder.encode(any())).thenReturn("Pa$$w0rd");

        assertThrows(EmailExistException.class, () -> userService.createUser(userToAdd));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().login("user").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword(" ");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").login(" ").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().login("user").email(" ").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").login("").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> userService.createUser(userFromUi));
    }

}
