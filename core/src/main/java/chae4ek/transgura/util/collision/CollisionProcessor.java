package chae4ek.transgura.util.collision;

import com.badlogic.gdx.physics.box2d.Contact;

public class CollisionProcessor {

  /**
   * Check the collision of 2 fixtures with custom user data (unorder)
   *
   * @return true if one of the fixtures with tag1 and the second one with tag2 (unorder), otherwise
   *     it returns false
   */
  public static boolean isFixturesCollision(
      final Contact contact, final String tag1, final String tag2) {
    final Object data1 = contact.getFixtureA().getUserData();
    final Object data2 = contact.getFixtureB().getUserData();
    final EntityData entityData1, entityData2;
    if (data1 != null && data1.getClass() == EntityData.class) entityData1 = (EntityData) data1;
    else return false;
    if (data2 != null && data2.getClass() == EntityData.class) entityData2 = (EntityData) data2;
    else return false;
    return tag1.equals(entityData1.tag) && tag2.equals(entityData2.tag)
        || tag2.equals(entityData1.tag) && tag1.equals(entityData2.tag);
  }

  /**
   * @return entity data or null if it's not an entity data
   */
  public static EntityData getEntityData(final Object userData) {
    if (userData != null && userData.getClass() == EntityData.class) return (EntityData) userData;
    return null;
  }
}
