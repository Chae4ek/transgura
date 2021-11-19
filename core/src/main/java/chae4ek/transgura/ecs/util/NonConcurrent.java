package chae4ek.transgura.ecs.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/** This method is not thread-safe */
@Documented
@Target(ElementType.METHOD)
@Inherited
public @interface NonConcurrent {}
