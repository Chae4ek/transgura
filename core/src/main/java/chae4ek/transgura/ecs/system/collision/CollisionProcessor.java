package chae4ek.transgura.ecs.system.collision;

import chae4ek.transgura.ecs.util.structures.Pair;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

public class CollisionProcessor {

  /**
   * Check the collision of 2 bodies with custom user data (unorder)
   *
   * @return the pair of the first body with userData1 and the second one with userData2 if it's the
   *     collision of them (unorder), otherwise it returns null
   */
  public static Pair<Body, Body> getBodysCollision(
      final Contact contact, final Object userData1, final Object userData2) {
    final Body body1 = contact.getFixtureA().getBody();
    final Body body2 = contact.getFixtureB().getBody();
    return body1.getUserData().equals(userData1) && body2.getUserData().equals(userData2)
        ? new Pair<>(body1, body2)
        : body1.getUserData().equals(userData2) && body2.getUserData().equals(userData1)
            ? new Pair<>(body2, body1)
            : null;
  }

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
