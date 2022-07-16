package io.github.lr.whatsapp.jms.messages;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad mensaje de grupo JMS.
 * 
 * @author lravanal
 *
 */
@Getter
@ToString
@NoArgsConstructor
public class JmsMessage implements Serializable {

	private static final long serialVersionUID = -3388757442194671625L;

	public static final String JMS_DESTINATION_NAME = "messages.q";

	private long groupId;
	private long userId;
	private String messageText;
	
	public JmsMessage(long groupId, long userId, String messageText) {
		this.groupId = groupId;
		this.userId = userId;
		this.messageText = messageText;
	}
	
}
