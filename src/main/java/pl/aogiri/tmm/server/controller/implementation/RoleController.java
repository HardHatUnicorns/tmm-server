package pl.aogiri.tmm.server.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.aogiri.tmm.server.controller.GenericController;
import pl.aogiri.tmm.server.dto.implementation.RoleDTO;
import pl.aogiri.tmm.server.exception.api.InvalidException;
import pl.aogiri.tmm.server.service.implementation.RoleService;

import java.util.Collection;
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
        if(!roleService.existById(id))
            throw new InvalidException("Role ID");

        return ResponseEntity.ok(roleService.findById(id).get());
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping("{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO){
        if(!roleService.existById(id))
            throw new InvalidException("Role ID");
        if(!id.equals(roleDTO.getId()))
            throw new InvalidException("Role IDs");
        RoleDTO updatedRoleDTO = roleService.update(roleDTO);

        return ResponseEntity.ok(updatedRoleDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.create(roleDTO));
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("{id}")
    public ResponseEntity updateRole(@PathVariable Long id){
        if(!roleService.existById(id))
            throw new InvalidException("Role ID");
        roleService.delete(id);

        return ResponseEntity.accepted().build();
    }


}
