package pl.aogiri.tmm.server.dto.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.dto.GenericDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.util.Mapper;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements GenericDTO<UserEntity> {

    private Long id;

    @NotBlank(message = "Login is required")
    @NotEmpty(message = "Login is required")
    @NotNull(message = "Login is required")
    @Size(min = 6, max = 32)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 64)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password is not valid")
    private String plainPassword;

    @NotBlank(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;

    private Collection<RoleDTO> roles = Collections.emptyList();

    public UserEntity toEntity(){
        return Mapper.map(this, UserEntity.class);
    }

}
