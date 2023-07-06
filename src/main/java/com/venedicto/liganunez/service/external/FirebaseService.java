package com.venedicto.liganunez.service.external;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseService {
	private Bucket bucket;
	@Value("${firebase.api.url}")
	private String url;
	
	@PostConstruct
	public void startFirebaseClient() throws IOException {
		FileInputStream apiCredentials = new FileInputStream("src/main/resources/firebase.json");
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(apiCredentials);
		
		FirebaseOptions options = FirebaseOptions.builder()
		    .setCredentials(googleCredentials)
		    .setStorageBucket(url)
		    .build();
		FirebaseApp.initializeApp(options);

		bucket = StorageClient.getInstance().bucket();
	}
	
	public String getImage(String tournamentId, String filename) throws FileNotFoundException {
		Blob blob = bucket.get(tournamentId+"/"+filename);
		if(blob == null) {
			throw new FileNotFoundException("La imagen no se subió al servidor");
		}
		
		//blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		return Base64.getEncoder().encodeToString(blob.getContent());
	}
	
	public void uploadImage(String id, String tournamentId, MultipartFile file) throws IOException {
		//Upload on the server
		BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucket.getName(), tournamentId+"/"+id))
	            					.setContentType(file.getContentType())
	            					.build();
		bucket.getStorage().create(blobInfo, file.getInputStream().readAllBytes());
	}
	
	public void deleteImage(String tournamentId, String filename) throws FileNotFoundException {
		Blob blob = bucket.get(tournamentId+"/"+filename);
		if(blob == null) {
			throw new FileNotFoundException("La imagen no se subió al servidor Firebase");
		}
		
		blob.delete(BlobSourceOption.generationMatch());
	}
}
