package com.gomo.app.test.extenstion;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;

public class MinioCleanUpExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
		MinioClient minioClient = applicationContext.getBean(MinioClient.class);
		String bucketName = applicationContext.getEnvironment().getProperty("minio.bucket-name");

		for (Result<Item> item : minioClient.listObjects(io.minio.ListObjectsArgs.builder().bucket(bucketName).recursive(true).build())) {
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.get().objectName()).build());
		}
	}
}
