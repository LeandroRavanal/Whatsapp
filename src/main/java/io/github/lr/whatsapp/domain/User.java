package io.github.lr.whatsapp.domain;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@RedisHash(User.USER)
public class User implements Serializable {
	
	private static final long serialVersionUID = 8163995317189348108L;

	public static final String USER = "USER";
	
	private static final String PARAM_NAME_REGEXP = "[a-zA-Z0-9_-]{1,20}";

	private static final int MIN_TOKEN_LENGTH = 1;
	private static final int MAX_TOKEN_LENGTH = 1_000_000;
	
	private static final String PARAM_NAME_MSG = "invalid name value";
	private static final String PARAM_TOKEN_MSG = "invalid token value";
	
	@Id private Long id;
	@Valid @NotNull(message=PARAM_NAME_MSG) @Pattern(regexp=PARAM_NAME_REGEXP, message=PARAM_NAME_MSG) private String name;
	@Valid @NotNull(message=PARAM_TOKEN_MSG) @Size(min=MIN_TOKEN_LENGTH, max=MAX_TOKEN_LENGTH, message=PARAM_TOKEN_MSG) @Indexed private String token;
	
	public User(Long id, String name, String token) {
		this.id = id;
		this.name = name;
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		User user = (User) o;
		if (id == null) {
			if (user.id != null)
				return false;
		} else if (!id.equals(user.id))
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
	
	public void setName(String name) {
		this.name = name;
	}

}
