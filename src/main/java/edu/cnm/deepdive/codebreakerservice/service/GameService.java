package edu.cnm.deepdive.codebreakerservice.service;

import edu.cnm.deepdive.codebreakerservice.model.dao.GameRepository;
import edu.cnm.deepdive.codebreakerservice.model.entity.Game;
import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private final GameRepository repository;

  public GameService(GameRepository repository) {
    this.repository = repository;
  }

  public Optional<Game> get(UUID id) {
    return repository.findById(id);
  }

  public Optional<Game> get(UUID key, User user) {
    return repository.findByExternalKeyAndUser(key, user);
  }

  public void delete(UUID id) {
    repository.deleteById(id);
  }

  public void delete(UUID key, User user) {
    repository
        .findByExternalKeyAndUser(key, user)
        .ifPresent(repository::delete);
  }


}
