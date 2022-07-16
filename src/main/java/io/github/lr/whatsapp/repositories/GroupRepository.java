package io.github.lr.whatsapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.lr.whatsapp.domain.Group;

/**
 * Repositorio de grupos.
 * 
 * @author lravanal
 *
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
	
}
