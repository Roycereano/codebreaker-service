package edu.cnm.deepdive.codebreakerservice.model.dao;

import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import edu.cnm.deepdive.codebreakerservice.view.ScoreSummary;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  String RANKING_STATISTICS_QUERY = "SELECT "
      + "  u.user_id AS userId, "
      + "  u.display_name AS displayName, "
      + "  u.external_key AS externalKey, "
      + "  gs.averageGuessCount, "
      + "  gs.averageTime "
      + "FROM user_profile AS u "
      + "INNER JOIN ("
      + "  SELECT "
      + "    g.user_id, "
      + "    AVG(gu.total_guesses) AS averageGuessCount, "
      + "    AVG(DATEDIFF(MILLISECOND, gu.first_guess, gu.last_guess)) AS averageTime "
      + "  FROM game AS g "
      + "  INNER JOIN ("
      + "    SELECT "
      + "      game_id, "
      + "      COUNT(*) as total_guesses, "
      + "      MIN(created) AS first_guess, "
      + "      MAX(created) AS last_guess, "
      + "      MAX(exact_matches) AS match_count "
      + "    FROM guess "
      + "    GROUP BY game_id"
      + "  ) AS gu ON gu.game_id = g.game_id "
      + "  WHERE "
      + "    g.length = :length "
      + "    AND g.pool_size = :poolSize "
      + "    AND gu.match_count = g.length "
      + "  GROUP BY g.user_id"
      + ") AS gs ON gs.user_id = u.user_id ";

  Optional<User> findByOauthKey(String oauthKey);

  Optional<User> findByExternalKey(UUID externalKey);

  Iterable<User> getAllByOrderByDisplayNameAsc();

  @Query(value = RANKING_STATISTICS_QUERY + "ORDER BY averageGuessCount ASC, averageTime ASC",
      nativeQuery = true)
  Iterable<ScoreSummary> getScoreSummariesOrderByGuessCount(int length, int poolSize);

  @Query(value = RANKING_STATISTICS_QUERY + "ORDER BY averageTime ASC, averageGuessCount ASC",
      nativeQuery = true)
  Iterable<ScoreSummary> getScoreSummariesOrderByTime(int length, int poolSize);


}


