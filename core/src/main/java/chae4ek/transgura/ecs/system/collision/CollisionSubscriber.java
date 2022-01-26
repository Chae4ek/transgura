package chae4ek.transgura.ecs.system.collision;

import com.badlogic.gdx.physics.box2d.Contact;

public interface CollisionSubscriber {

  void beginContact(Contact contact);

  void endContact(Contact contact);
}
