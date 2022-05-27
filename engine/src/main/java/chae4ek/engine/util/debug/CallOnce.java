package chae4ek.engine.util.debug;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/** This method must be called only once */
@Documented
@Target(ElementType.METHOD)
@Inherited
public @interface CallOnce {}
