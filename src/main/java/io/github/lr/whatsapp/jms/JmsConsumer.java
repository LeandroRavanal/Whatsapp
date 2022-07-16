package io.github.lr.whatsapp.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import io.github.lr.whatsapp.jms.messages.JmsMessage;
import io.github.lr.whatsapp.managers.NotificationManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Consumidor de mensajes de grupo JMS.
 * 
 * @author lravanal
 *
 */
@Slf4j
@Component
@ConditionalOnProperty(value="app.notification-sender", havingValue="true")
public class JmsConsumer {
	
	@Autowired private NotificationManager notificationManager;
	
	@JmsListener(destination=JmsMessage.JMS_DESTINATION_NAME)
    public void receiveMessage(JmsMessage jmsMessage) {
		try {
			log.debug("Message={} has been received", jmsMessage);
    	
			notificationManager.sendNotificationGroup(jmsMessage.getGroupId(), jmsMessage.getUserId(), jmsMessage.getMessageText());

		} catch (Exception e) {
			log.error("Notification could not be sent. " + jmsMessage.toString(), e);
		
		}
    }
	
}
