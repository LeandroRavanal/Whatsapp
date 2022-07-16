package io.github.lr.whatsapp.managers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import io.github.lr.whatsapp.domain.Group;
import io.github.lr.whatsapp.domain.User;
import io.github.lr.whatsapp.repositories.GroupRepository;
import io.github.lr.whatsapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Administrador encargado de la notificacion del mensaje para cada integrante del grupo. Realiza validaciones de dominio.
 * 
 * @author lravanal
 *
 */
@Slf4j
@Component
@ConditionalOnProperty(value="app.notification-sender", havingValue="true")
public class NotificationManager {

	@Autowired private UserRepository userRepository;
	@Autowired private GroupRepository groupRepository;
	
	@Autowired private FirebaseMessaging firebaseMessaging;
	
	public void sendNotificationGroup(Long groupId, Long userId, String messageText) {
		Optional<Group> optGroup = groupRepository.findById(groupId);
		if (!optGroup.isPresent()) {
			log.error("Group with id={} is not present in repository");
			
			return;
		}

		optGroup.get().getUserIds().stream().filter(id -> !id.equals(userId)).forEach(id -> sendNotification(userId, id, messageText));
	}

	private void sendNotification(Long userIdFrom, Long userIdTo, String messageText) {
		Optional<User> optUserFrom = userRepository.findById(userIdFrom);
		if (!optUserFrom.isPresent()) {
			log.error("User(from) with id={} is not present in repository", userIdFrom);
			
			return;
		}
		
		Optional<User> optUserTo = userRepository.findById(userIdTo);
		if (!optUserTo.isPresent()) {
			log.error("User(to) with id={} is not present in repository", userIdTo);
			
			return;
		}
		
		User userFrom = optUserFrom.get();
		User userTo = optUserTo.get();
		
		Notification notification = Notification.builder().setTitle(userFrom.getName()).setBody(messageText).build();
		
		Message message = Message.builder().setToken(userTo.getToken()).setNotification(notification).build();
		
		try {
			firebaseMessaging.send(message);
		
			log.debug("Notification to User={} from User={} MessageText={} has been sent", userFrom.getName(), userTo.getName(), messageText);
		
		} catch (FirebaseMessagingException fme) {
			log.error("Notification could not be sent. User=" + userFrom.getName() + " MessageText=" + messageText, fme);
			
		}
	}

}
