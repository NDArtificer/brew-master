package com.artificer.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.artificer.services.FotoStorageService;
import com.artificer.storage.StorageProperties.StorageType;
import com.artificer.storage.local.FotoStorageLocal;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;

//	@Bean
//	@ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
//	public AmazonS3 amazonS3() {
//		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdAccessKey(),
//				storageProperties.getS3().getIdSecretAccessKey());
//
//		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
//				.withRegion(storageProperties.getS3().getRegion()).build();
//	}

	@Bean
	public FotoStorageService fotoStorageService() {
		if (StorageType.S3.equals(storageProperties.getType())) {
			// return new S3FotoStorageService();
			return new FotoStorageLocal(storageProperties);
		} else {
			return new FotoStorageLocal(storageProperties);
		}
	}

}
