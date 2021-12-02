package chae4ek.transgura.ecs.util.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This method is deferred that means it will be called after update/fixedUpdate in a single thread
 */
@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DeferredEvent {}
