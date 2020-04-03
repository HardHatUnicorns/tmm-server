package pl.aogiri.tmm.server.dto.implementation;

import lombok.Data;
import pl.aogiri.tmm.server.dto.GenericDTO;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.util.Mapper;

@Data
public class RegisterTokenDTO implements GenericDTO<RoleEntity> {

    private Long id;
    private String name;

    public RoleEntity toEntity(){
        return Mapper.map(this, RoleEntity.class);
    }

}
