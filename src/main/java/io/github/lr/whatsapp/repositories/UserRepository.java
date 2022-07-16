package io.github.lr.whatsapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.lr.whatsapp.domain.User;

/**
 * Repositorio de usuarios. Permite busqueda por token.
 * 
 * @author lravanal
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findByToken(String token);
	
}
