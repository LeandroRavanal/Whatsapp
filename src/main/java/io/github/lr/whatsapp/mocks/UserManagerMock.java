package io.github.lr.whatsapp.mocks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.github.lr.whatsapp.domain.Group;
import io.github.lr.whatsapp.domain.User;
import io.github.lr.whatsapp.repositories.GroupRepository;
import io.github.lr.whatsapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class UserManagerMock {

	@Autowired private UserRepository userRepository;
	@Autowired private GroupRepository groupRepository;

	public Long process(User user) {
		log.debug("Executing mock user manager...");
		
		Long id = 0L;
		
		Group group = groupRepository.findById(1L).get();
		List<User> users = userRepository.findByToken(user.getToken());
		
		if (!users.isEmpty()) {
			log.debug("Updating mock user name...");
			
			//It should be just one
			for (User u : users) {
				u.setName(user.getName());
				
				id = update(group, u);
			}
		
		} else {
			id = update(group, user);

		}
		
		return id;
	}
	
	private Long update(Group group, User user) {
		user = userRepository.save(user);
		
		log.debug("{} was saved", user);

		group.addUser(user.getId());
		
		group = groupRepository.save(group);
		
		log.debug("{} was saved", group);
		
		return user.getId();
	}
	
}
