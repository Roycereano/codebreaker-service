package edu.cnm.deepdive.codebreakerservice.model.dao;

import edu.cnm.deepdive.codebreakerservice.model.entity.Game;
import edu.cnm.deepdive.codebreakerservice.model.entity.Guess;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessRepository extends JpaRepository<Guess, UUID> {

  Optional<Guess> findByGameAndExternalKey(Game game, UUID externalKey);

}
