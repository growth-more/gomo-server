package com.gomo.app.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

import com.gomo.app.test.config.RabbitMQConfig;
import com.gomo.app.test.extenstion.RabbitMQCleanUpExtension;

@ExtendWith(RabbitMQCleanUpExtension.class)
@Import(RabbitMQConfig.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WithRabbitMQ {
}
