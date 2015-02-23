package mx.com.dxesoft.diidxavote.repository;

import mx.com.dxesoft.diidxavote.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
