package pl.aogiri.tmm.server.dto.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.dto.GenericDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.util.Mapper;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements GenericDTO<UserEntity> {

    private Long id;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String plainPassword;

    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;

    private Collection<RoleDTO> roles = Collections.emptyList();

    public UserEntity toEntity(){
        return Mapper.map(this, UserEntity.class);
    }

}
