package io.github.lr.whatsapp.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lr.whatsapp.managers.MessageManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Punto de entrada de la publicacion de mensaje de grupo. Contiene las validaciones correspondientes de cada parametro.
 * 
 * @author lravanal
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class FrontController {
	
	private static final long MIN_ID_VALUE = Long.MIN_VALUE;
	private static final long MAX_ID_VALUE = Long.MAX_VALUE;
	private static final int MIN_MESSAGE_LENGTH = 1;
	private static final int MAX_MESSAGE_LENGTH = 1_000;
	
	private static final String PARAM_GROUPID_MSG = "invalid groupId value";
	private static final String PARAM_USERID_MSG = "invalid userId value";
	private static final String PARAM_MESSAGE_MSG = "invalid message value";
	
	private static final String RESPONSE_OK_MSG = "The action has been performed";
	
	@Autowired private MessageManager messageManager;
	
	@PostMapping(value="/group/{groupId}/user/{userId}", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> message(
			@Valid @Min(value=MIN_ID_VALUE, message=PARAM_GROUPID_MSG) @Max(value=MAX_ID_VALUE, message=PARAM_GROUPID_MSG) @PathVariable Long groupId, 
			@Valid @Min(value=MIN_ID_VALUE, message=PARAM_USERID_MSG) @Max(value=MAX_ID_VALUE, message=PARAM_USERID_MSG) @PathVariable Long userId, 
			@Valid @NotNull(message=PARAM_MESSAGE_MSG) @Size(min=MIN_MESSAGE_LENGTH, max=MAX_MESSAGE_LENGTH, message=PARAM_MESSAGE_MSG) @RequestBody String messageText) {
		log.debug("Request received, groupId={} userId={} messageText={}", groupId, userId, messageText);
		
		messageManager.processMessage(groupId, userId, messageText);
		
		return ResponseEntity.ok(RESPONSE_OK_MSG);
	}

}
