package intouch.repository;

import intouch.model.Post;
import intouch.repository.base.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PostsRepository extends CrudRepository<Post, UUID> {
    List<Post> findAllByAuthorId(UUID id);
}
