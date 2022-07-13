package io.github.lr.whatsapp.managers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.github.lr.whatsapp.domain.Group;
import io.github.lr.whatsapp.domain.User;
import io.github.lr.whatsapp.jms.JmsProducer;
import io.github.lr.whatsapp.repositories.GroupRepository;
import io.github.lr.whatsapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class MessageManager {

	@Autowired private GroupRepository groupRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private JmsProducer jmsProducer;
	
	public void processMessage(Long groupId, Long userId, String messageText) {
		Optional<Group> optGroup = groupRepository.findById(groupId);
		if (!optGroup.isPresent()) {
			log.error("Group id={} does not exists", groupId);
			
			throw new RuntimeException(String.format("Group id=%d does not exists", groupId));
		}
		
		Optional<User> optUser = userRepository.findById(userId);
		if (!optUser.isPresent()) {
			log.error("User id={} does not exists", userId);

			throw new RuntimeException(String.format("User id=%d does not exists", userId));
		}
		
		Group group = optGroup.get();
		if (!group.getUserIds().stream().anyMatch(id -> id.equals(userId))) {
			log.error("User id={} does not exists in group id={}", userId, groupId);
			
			throw new RuntimeException(String.format("User id=%d does not exists in group id=%d", userId, groupId));
		}
		
		group.addMessage(userId, messageText);
		
		groupRepository.save(group);
		
		jmsProducer.sendMessage(groupId, userId, messageText);
	}
	
}
