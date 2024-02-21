package intouch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Post {
    private UUID id;
    private User author;
    private String text;
    private Instant creationDate;
}