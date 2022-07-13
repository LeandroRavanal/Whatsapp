package io.github.lr.whatsapp.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.github.lr.whatsapp.domain.Group;
import io.github.lr.whatsapp.repositories.GroupRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GroupInitMock {

	@Autowired private GroupRepository groupRepository;
	
	@EventListener
	public void init(ContextRefreshedEvent event) {
		try {
			if (!groupRepository.existsById(1L)) {
				groupRepository.save(new Group(1L, "Group_N1"));
				
				log.info("Mock group Group_N1 has been saved");
			}
		
		} catch (IllegalArgumentException iae) {
			log.error("Mock group could not be retrieved / saved", iae);
			
		}
	}
	
}
