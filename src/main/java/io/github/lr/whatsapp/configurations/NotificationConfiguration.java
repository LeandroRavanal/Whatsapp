package io.github.lr.whatsapp.configurations;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Configuracion del servicio de notificacion (firebase).
 * 
 * @author lravanal
 *
 */
@Configuration
@ConditionalOnProperty(value="app.notification-sender", havingValue="true")
public class NotificationConfiguration {

	@Bean
	public FirebaseMessaging firebaseMessaging() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
		FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions, "lr-whatsapp");
		return FirebaseMessaging.getInstance(firebaseApp);
	}

}
