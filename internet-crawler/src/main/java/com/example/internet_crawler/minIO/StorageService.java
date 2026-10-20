package com.example.internet_crawler.minIO;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
	
	private final MinioClient minioClient;
	
	@Value("${minio.bucket-name}")
    private String bucketName;
	
	public String uploadHTML(String html) throws Exception{
		boolean found = minioClient.bucketExists( 
				BucketExistsArgs.builder().bucket( bucketName ).build() 
				);
		if(!found) {
			minioClient.makeBucket( MakeBucketArgs.builder().bucket( bucketName ).build() );
		}
		String key = UUID.randomUUID() + ".html";
		byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
		
		try(ByteArrayInputStream stream = new ByteArrayInputStream( bytes )){
			minioClient.putObject( 
					PutObjectArgs
					.builder()
					.bucket( bucketName )
					.object( key )
					.stream( stream, bytes.length, -1 )
					.contentType( "text/html" )
					.build()
					);
		}
		return key;
	}
	
}
