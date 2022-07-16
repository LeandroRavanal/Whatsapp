package io.github.lr.whatsapp;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.lr.whatsapp.configurations.CacheConfiguration;
import io.github.lr.whatsapp.domain.Group;
import io.github.lr.whatsapp.domain.User;
import io.github.lr.whatsapp.jms.JmsProducer;
import io.github.lr.whatsapp.managers.MessageManager;
import io.github.lr.whatsapp.repositories.GroupRepository;
import io.github.lr.whatsapp.repositories.UserRepository;

/**
 * Test de validaciones de dominio asociadas al administrador de mensaje de grupo.
 * 
 * @author lravanal
 *
 */
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ComponentScan(basePackages={"io.github.lr.whatsapp.managers", "io.github.lr.whatsapp.repositories"})
@TestPropertySource(properties={"app.front-controller=true", "app.notification-sender=false", "spring.redis.host=localhost", "spring.redis.port=6378"})
@Import({CacheConfiguration.class, TestRedisConfiguration.class})
public class MessageManagerTest {
	
	@Autowired private GroupRepository groupRepository;
	@Autowired private UserRepository userRepository;
	
	@Autowired private MessageManager messageManager;
	
	@MockBean private JmsProducer jmsProducer;

	@BeforeEach
	public void init() {
		userRepository.save(buildUser(1L, "Leandro", "TOKEN1"));
		userRepository.save(buildUser(2L, "Hernan", "TOKEN2"));
		userRepository.save(buildUser(3L, "Andres", "TOKEN3"));
		
		groupRepository.save(buildGroup(1L, "Grupo1", new Long[]{1L, 2L}));
		groupRepository.save(buildGroup(2L, "Grupo2", new Long[]{2L, 3L}));
	}
	
	private User buildUser(Long id, String name, String token) {
		User u = new User(id, name, token);
		return u;
	}
	
	private Group buildGroup(Long id, String name, Long[] userIds) {
		Group g = new Group(id, name);
		Arrays.stream(userIds).forEach(u -> g.addUser(u));
		return g;
	}

	@Test
	public void newMessage_OkTest() {
		Long groupId = 1L;
		Long userId = 1L;
		String messageText = "New Mensaje";
		
		messageManager.processMessage(groupId, userId, messageText);
		
		Assertions.assertEquals(groupRepository.findById(groupId).get().getMessages().get(0).getMessageText(), messageText);
	}
	
	@Test
	public void newMessage_noUserTest() {
		Long groupId = 1L;
		Long userId = 4L;
		String messageText = "New Mensaje";
		
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			messageManager.processMessage(groupId, userId, messageText);
	    });	
		
		String expectedMessage = "User id=4 does not exists";
		String actualMessage = exception.getMessage();
		
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test 
	public void newMessage_noGroupTest() {
		Long groupId = 3L;
		Long userId = 1L;
		String messageText = "New Mensaje";
		
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
			messageManager.processMessage(groupId, userId, messageText);
	    });
		
		String expectedMessage = "Group id=3 does not exists";
		String actualMessage = exception.getMessage();
		
		Assertions.assertTrue(actualMessage.contains(expectedMessage));		
	}
	
}
