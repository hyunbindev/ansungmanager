package com.ansung.ansung.service.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ansung.ansung.controller.CustomerController;
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
			FileInputStream serviceAccout = new FileInputStream("./src/main/resources/firebase/ansungmanager-a4ed3-firebase-adminsdk-fbsvc-1062e2fae9.json");
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
