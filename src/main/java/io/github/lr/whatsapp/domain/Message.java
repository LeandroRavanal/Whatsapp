package io.github.lr.whatsapp.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad Mensaje. Asocia el id usuario con el texto del mensaje.
 * 
 * @author lravanal
 *
 */
@Getter
@ToString
@NoArgsConstructor
public class Message implements Serializable {

	private static final long serialVersionUID = 6219803082231492763L;

	private Long userId;
	private String messageText;
	
	public Message(Long userId, String messageText) {
		this.userId = userId;
		this.messageText = messageText;
	}
	
}
