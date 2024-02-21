package intouch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PostDto {
    private UUID id;
    private UserDto author;
    private String text;
    private Instant creationDate;
}