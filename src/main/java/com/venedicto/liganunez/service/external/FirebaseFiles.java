package com.venedicto.liganunez.service.external;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseFiles {
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
	
	public String getImage(String tournamentId, String filename) {
		Blob blob = bucket.get(tournamentId+"/"+filename);
		blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		return "https://storage.googleapis.com/" + blob.getBucket() + "/" + blob.getName();
	}
}
