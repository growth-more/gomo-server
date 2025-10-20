package com.gomo.app.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.gomo.app.test.container.CoreDBContainerInitializer;
import com.gomo.app.test.container.MetaDBContainerInitializer;
import com.gomo.app.test.container.MinioContainerInitializer;
import com.gomo.app.test.container.RabbitMQContainerInitializer;
import com.gomo.app.test.container.RedisContainerInitializer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
	CoreDBContainerInitializer.class,
	MetaDBContainerInitializer.class,
	RedisContainerInitializer.class,
	RabbitMQContainerInitializer.class,
	MinioContainerInitializer.class
})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface IntegrationTest {
}
