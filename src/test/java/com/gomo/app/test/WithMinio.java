package com.gomo.app.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import com.gomo.app.test.extenstion.MinioCleanUpExtension;

@ExtendWith(MinioCleanUpExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WithMinio {
}
