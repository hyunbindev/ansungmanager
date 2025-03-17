package com.ansung.ansung.service.firebase;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirebaseInitialization {
	@PostConstruct
	public void initialize() {
		try {
			InputStream serviceAccout = new ClassPathResource("firebase/ansungmanager-a4ed3-firebase-adminsdk-fbsvc-5d0a8af079.json").getInputStream();
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccout))
					.build();
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
