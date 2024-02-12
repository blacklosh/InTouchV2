package intouch.repository;

import intouch.model.Post;
import intouch.repository.base.CrudRepository;

import java.util.UUID;

public interface PostsRepository extends CrudRepository<Post, UUID> {
}
