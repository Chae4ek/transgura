package chae4ek.transgura.engine.util.collision;

import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.ObjectSet;

public final class CollisionListener implements ContactListener {

  private static final GameAlert gameAlert = new GameAlert(CollisionListener.class);

  private final ObjectSet<CollisionSubscriber> subscribers = new ObjectSet<>();

  public void addCollisionSubscriber(final CollisionSubscriber subscriber) {
    if (!subscribers.add(subscriber))
      gameAlert.warn("The collision subscriber {} has already subscribed", subscriber);
  }

  public void removeCollisionSubscriber(final CollisionSubscriber subscriber) {
    if (!subscribers.remove(subscriber))
      gameAlert.warn("The collision subscriber {} is not subscribed", subscriber);
  }

  @Override
  public void beginContact(final Contact contact) {
    for (final CollisionSubscriber subscriber : subscribers) subscriber.beginContact(contact);
  }

  @Override
  public void endContact(final Contact contact) {
    for (final CollisionSubscriber subscriber : subscribers) subscriber.endContact(contact);
  }

  @Override
  public void preSolve(final Contact contact, final Manifold oldManifold) {}

  @Override
  public void postSolve(final Contact contact, final ContactImpulse impulse) {}
}
