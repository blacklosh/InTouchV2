package intouch.repository;

import intouch.model.FileInfo;
import intouch.repository.base.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<FileInfo, UUID> {
}
