package mx.com.dxesoft.diidxavote.repository;

import mx.com.dxesoft.diidxavote.domain.Elector;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Elector entity.
 */
public interface ElectorRepository extends JpaRepository<Elector,Long>{

}
