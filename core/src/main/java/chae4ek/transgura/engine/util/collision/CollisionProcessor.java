package chae4ek.transgura.engine.util.collision;

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
    return userData1.equals(data1) && userData2.equals(data2)
        || userData2.equals(data1) && userData1.equals(data2);
  }
}
