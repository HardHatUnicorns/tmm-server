package pl.aogiri.tmm.server.dto.implementation;

import lombok.Data;
import pl.aogiri.tmm.server.dto.GenericDTO;
import pl.aogiri.tmm.server.entity.implementation.PrivilegeEntity;
import pl.aogiri.tmm.server.util.Mapper;

@Data
public class PrivilegeDTO implements GenericDTO<PrivilegeEntity> {

    private Long id;
    private String name;

    public PrivilegeEntity toEntity(){
        return Mapper.map(this, PrivilegeEntity.class);
    }
}
