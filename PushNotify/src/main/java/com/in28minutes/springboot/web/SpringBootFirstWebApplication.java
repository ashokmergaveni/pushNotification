package com.in28minutes.springboot.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@ComponentScan("com.in28minutes.springboot.web")
public class SpringBootFirstWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringBootFirstWebApplication.class, args);
		
		/*FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.getApplicationDefault())
			   // .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
			    .build();
			FirebaseApp.initializeApp(options);*/
		
		
		String path = new File(".").getAbsolutePath();
		System.out.println(path);
		FileInputStream serviceAccount =
				  //new FileInputStream("D:\\Ashok\\camdenhqo-firebase-adminsdk-i2lpn-8f66aa25dd.json");
				
				new FileInputStream(path+"/camdenhqo-firebase-adminsdk-i2lpn-8f66aa25dd.json");
				FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  
				  .build();

				FirebaseApp.initializeApp(options);

	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootFirstWebApplication.class);
	}
	
}
