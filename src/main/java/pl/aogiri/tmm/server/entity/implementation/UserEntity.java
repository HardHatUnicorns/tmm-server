package pl.aogiri.tmm.server.entity.implementation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.GenericEntity;
import pl.aogiri.tmm.server.entity.listener.RegisterListener;
import pl.aogiri.tmm.server.util.Mapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Entity(name = "users")
@EntityListeners(RegisterListener.class)
@Data
@AllArgsConstructor
@Builder
public class UserEntity implements GenericEntity<UserDTO> {
    public static final String ENTITY = "users";

    public UserEntity() {
        super();
        this.enabled = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;


    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    private String password;

    @Column(columnDefinition = "Boolean default false", nullable = false, insertable = false)
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles = Collections.emptyList();

    @Override
    public UserDTO toDTO() {
        return Mapper.map(this, UserDTO.class);
    }
}
