package pl.aogiri.tmm.server.entity.implementation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.dto.implementation.RoleDTO;
import pl.aogiri.tmm.server.entity.GenericEntity;
import pl.aogiri.tmm.server.util.Mapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity implements GenericEntity<RoleDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @Override
    public RoleDTO toDTO() {
        return Mapper.map(this, RoleDTO.class);
    }
}
