package intouch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Post {
    private UUID id;
    private UUID authorId;
    private String text;
}
