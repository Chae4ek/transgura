package chae4ek.engine.util.collision;

import com.badlogic.gdx.physics.box2d.Contact;

public interface CollisionSubscriber {

  void beginContact(Contact contact);

  void endContact(Contact contact);
}
