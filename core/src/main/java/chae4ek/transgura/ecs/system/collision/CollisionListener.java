package chae4ek.transgura.ecs.system.collision;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.util.HashSet;
import java.util.Set;

public class CollisionListener implements ContactListener {

  private static final transient GameAlert gameAlert = new GameAlert(CollisionListener.class);

  private final Set<CollisionSubscriber> subscribers = new HashSet<>();

  public void addCollisionSubscriber(final CollisionSubscriber subscriber) {
    if (!subscribers.add(subscriber))
      gameAlert.warn(
          GameErrorType.COLLISION_SUBSCRIBER_HAS_ALREADY_EXIST, "subscriber: " + subscriber);
  }

  public void removeCollisionSubscriber(final CollisionSubscriber subscriber) {
    if (!subscribers.remove(subscriber))
      gameAlert.warn(
          GameErrorType.COLLISION_SUBSCRIBER_DOES_NOT_EXIST, "subscriber: " + subscriber);
  }

  @Override
  public void beginContact(final Contact contact) {
    for (final CollisionSubscriber subscriber : subscribers) subscriber.beginContact(contact);
  }

  @Override
  public void endContact(final Contact contact) {}

  @Override
  public void preSolve(final Contact contact, final Manifold oldManifold) {}

  @Override
  public void postSolve(final Contact contact, final ContactImpulse impulse) {}
}
