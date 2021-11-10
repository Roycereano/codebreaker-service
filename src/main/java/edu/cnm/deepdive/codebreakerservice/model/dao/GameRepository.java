package edu.cnm.deepdive.codebreakerservice.model.dao;

import edu.cnm.deepdive.codebreakerservice.model.entity.Game;
import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, UUID> {

  Optional<Game> findByExternalKey(UUID key);

  Optional<Game> findByExternalKeyAndUser(UUID key, User user);
}