package pl.aogiri.tmm.server.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aogiri.tmm.server.controller.GenericController;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.service.implementation.RegisterService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class RegisterController extends GenericController {

    private final static Logger logger = Logger.getLogger(RegisterController.class.getCanonicalName());

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        Optional<UserDTO> optionalUserDTO = registerService.registerUser(userDTO);

        if(optionalUserDTO.isPresent())
            userDTO = optionalUserDTO.get();

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "activate")
    public ResponseEntity activateUser(@RequestParam String token, @RequestParam String email){
        registerService.activateAccountByTokenAndEmail(token, email);
        return ResponseEntity.ok().build();
    }

}
