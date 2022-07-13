package io.github.lr.whatsapp.mocks;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lr.whatsapp.domain.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class UserFrontControllerMock {

	@Autowired private UserManagerMock userManagerMock;
	
	@PostMapping(value="/user", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> user(@RequestBody @Valid User user) {
		log.debug("Request received, {}", user);
		
		Long userId = userManagerMock.process(user);
		
		return ResponseEntity.ok(userId.toString());
	}
	
}
