package pl.aogiri.tmm.server.entity.implementation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.dto.implementation.PrivilegeDTO;
import pl.aogiri.tmm.server.entity.GenericEntity;
import pl.aogiri.tmm.server.util.Mapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity(name = "privileges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivilegeEntity implements GenericEntity<PrivilegeDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;

    @Override
    public PrivilegeDTO toDTO() {
        return Mapper.map(this, PrivilegeDTO.class);
    }
}
