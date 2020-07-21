package pl.aogiri.tmm.server.dto.implementation;

import lombok.Data;
import pl.aogiri.tmm.server.dto.GenericDTO;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.util.Mapper;

import java.util.Collection;

@Data
public class RoleDTO implements GenericDTO<RoleEntity> {

    private Long id;
    private String name;
    private Collection<PrivilegeDTO> privileges;

    public RoleEntity toEntity(){
        return Mapper.map(this, RoleEntity.class);
    }

}
