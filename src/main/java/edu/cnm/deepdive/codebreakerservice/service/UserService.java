package edu.cnm.deepdive.codebreakerservice.service;

import edu.cnm.deepdive.codebreakerservice.model.dao.UserRepository;
import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt source) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(source.getSubject(), source.getClaimAsString("name")),
        source.getTokenValue(), grants);
  }

  public User getOrCreate(String oauthKey, String displayName) {
    return repository
        .findByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user
              .setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          return repository.save(user);
        });
  }

  public User getCurrentUser() {
    return (User) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
  }

  public Optional<User> get(UUID id) {
    return repository.findById(id);
  }

  public Optional<User> getByExternalKey(UUID key) {
    return repository.findByExternalKey(key);
  }

  public Iterable<User> getAll() {
    return repository.getAllByOrderByDisplayNameAsc();
  }

  public User save(User user) {
    return repository.save(user);
  }

  public void delete(User user) {
    repository.delete(user);
  }

  public User update(User updatedUser, User user) {
    if (updatedUser.getDisplayName() != null) {
      user.setDisplayName(updatedUser.getDisplayName());
    }
      return save(user);
  }
}
