package intouch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class FileInfo {
    private UUID id;
    private String originalFileName;
    private String storageFileName;
    private Long size;
    private String type;
}
