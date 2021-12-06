package edu.cnm.deepdive.codebreakerservice.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public interface ScoreSummary {

  @JsonIgnore
  UUID getUserId();

  @JsonIgnore
  UUID getExternalKey();

  String getDisplayName();

  double getAverageGuessCount();

  double getAverageTime();
}
