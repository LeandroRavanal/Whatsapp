package io.github.lr.whatsapp.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import io.github.lr.whatsapp.jms.messages.JmsMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class JmsProducer {

	@Autowired private JmsTemplate jmsTemplate;
	
	public void sendMessage(Long groupId, Long userId, String messageText) {
		JmsMessage jmsMessage = new JmsMessage(groupId, userId, messageText);
		
		try {
			jmsTemplate.convertAndSend(JmsMessage.JMS_DESTINATION_NAME, jmsMessage);
		
			log.debug("Message={} has been sent", jmsMessage);
		
		} catch (Exception e) {
			log.error("Message could not be sent. " + jmsMessage.toString(), e);
		
		}
	}
	
}
