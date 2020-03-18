package ru.market.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeRequestMethod {
    String url();
    Method[] methods();

    enum Method{
        POST, PUT, GET, DELETE
    }
}
