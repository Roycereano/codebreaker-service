package edu.cnm.deepdive.codebreakerservice;

import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;

@Entity
public class Game {

  private UUID id;

  private UUID externalKey;

  private User user;

  private Date created;

  private String pool;

  private int length;

  private String text;

}
