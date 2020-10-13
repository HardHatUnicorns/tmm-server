package pl.aogiri.tmm.server.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.RoleDAO;
import pl.aogiri.tmm.server.dto.implementation.RoleDTO;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.service.GenericService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements GenericService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO){
        this.roleDAO = roleDAO;
    }

    public Optional<RoleDTO> findById(Long id){
        return roleDAO.findById(id).map(RoleEntity::toDTO);
    }

    public Collection<RoleDTO> findAll(){
        return StreamSupport.stream(roleDAO.findAll().spliterator(), true)
                        .map(RoleEntity::toDTO)
                        .collect(Collectors.toSet());
    }

    public boolean existById(Long id){
        return roleDAO.existsById(id);
    }

    public RoleDTO create(RoleDTO roleDTO){
        RoleEntity roleEntity = roleDTO.toEntity();
        roleDAO.save(roleEntity);
        return roleEntity.toDTO();
    }

    public RoleDTO update(RoleDTO roleDTO) {
        return create(roleDTO);
    }

    public void delete(Long id) {
        roleDAO.deleteById(id);
    }

}
