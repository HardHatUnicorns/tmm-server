package pl.aogiri.tmm.server.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.aogiri.tmm.server.controller.GenericController;
import pl.aogiri.tmm.server.dto.implementation.RoleDTO;
import pl.aogiri.tmm.server.service.implementation.RoleService;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("role")
public class RoleController extends GenericController {

    private final static Logger logger = Logger.getLogger(RoleController.class.getCanonicalName());

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping
    public ResponseEntity<Collection<RoleDTO>> getRoles(){
        return ResponseEntity.ok(roleService.findAll());
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping("{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id){
        Optional<RoleDTO> roleDTO = roleService.findById(id);

        return ResponseEntity.ok(roleService.findById(id).get());
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @PostMapping
    public ResponseEntity<RoleDTO> getRoles(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.create(roleDTO));
    }


}
