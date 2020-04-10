package pl.aogiri.tmm.server.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aogiri.tmm.server.controller.GenericController;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.service.implementation.UserService;

import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserController extends GenericController {

    private final static Logger logger = Logger.getLogger(UserController.class.getCanonicalName());

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(value = "authentication")
    @PreAuthorize("isAuthenticated()")
    public void authentication(){
        logger.info("User try to authentication");
    }

}
