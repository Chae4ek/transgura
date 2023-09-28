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
    final EntityData entityData1 = getEntityData(contact.getFixtureA().getUserData());
    if (entityData1 == null) return false;

    final EntityData entityData2 = getEntityData(contact.getFixtureB().getUserData());
    if (entityData2 == null) return false;

    return tag1.equals(entityData1.tag) && tag2.equals(entityData2.tag)
        || tag2.equals(entityData1.tag) && tag1.equals(entityData2.tag);
  }

  /**
   * Check user data from fixture A then B.
   *
   * @return entity data with the tag from the contact or null if there's no entity data with the
   *     tag
   */
  public static EntityData getEntityData(final Contact contact, final String tag) {
    EntityData entityData = getEntityData(contact.getFixtureA().getUserData());
    if (entityData != null && tag.equals(entityData.tag)) {
      return entityData;
    }

    entityData = getEntityData(contact.getFixtureB().getUserData());
    if (entityData != null && tag.equals(entityData.tag)) {
      return entityData;
    }

    return null;
  }

  /**
   * @return entity data or null if it's not an entity data
   */
  public static EntityData getEntityData(final Object userData) {
    if (userData != null && userData.getClass() == EntityData.class) return (EntityData) userData;
    return null;
  }
}
