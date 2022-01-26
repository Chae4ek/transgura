package chae4ek.transgura.ecs.system.collision;

import com.badlogic.gdx.physics.box2d.Contact;

public class CollisionProcessor {

  /**
   * Check the collision of 2 fixtures with custom user data (unorder)
   *
   * @return true if one of the fixtures with userData1 and the second one with userData2 (unorder),
   *     otherwise it returns false
   */
  public static boolean isFixturesCollision(
      final Contact contact, final Object userData1, final Object userData2) {
    final Object data1 = contact.getFixtureA().getUserData();
    final Object data2 = contact.getFixtureB().getUserData();
    return data1.equals(userData1) && data2.equals(userData2)
        || data1.equals(userData2) && data2.equals(userData1);
  }
}
