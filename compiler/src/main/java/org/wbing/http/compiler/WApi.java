package org.wbing.http.compiler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangbing
 * @date 2018/8/30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface WApi {
}
