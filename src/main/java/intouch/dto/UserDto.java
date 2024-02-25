package intouch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private UUID avatarId;
}
