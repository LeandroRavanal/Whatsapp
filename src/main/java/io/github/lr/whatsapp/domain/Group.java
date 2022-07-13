package io.github.lr.whatsapp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@RedisHash(Group.GROUP)
public class Group implements Serializable {

	private static final long serialVersionUID = -5868785440054599828L;

	public static final String GROUP = "GROUP";
	
	@Id private Long id;
	private String name;
	private Set<Long> userIds = new HashSet<>();
	private List<Message> messages = new LinkedList<>();
	
	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Group group = (Group) o;
		if (id == null) {
			if (group.id != null)
				return false;
		} else if (!id.equals(group.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void addUser(Long userId) {
		userIds.add(userId);
	}
	
	public void addMessage(Long userId, String messageText) {
		messages.add(0, new Message(userId, messageText));
	}

}
