package pl.aogiri.tmm.server.entity.implementation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.dto.implementation.RegisterTokenDTO;
import pl.aogiri.tmm.server.entity.GenericEntity;
import pl.aogiri.tmm.server.entity.listener.TokenListener;
import pl.aogiri.tmm.server.util.Mapper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tokens")
@EntityListeners(TokenListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenEntity implements GenericEntity<RegisterTokenDTO> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tokens_users",
            joinColumns = @JoinColumn(name = "token_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private UserEntity user;

    @Override
    public RegisterTokenDTO toDTO() {
        return Mapper.map(this, RegisterTokenDTO.class);
    }
}
