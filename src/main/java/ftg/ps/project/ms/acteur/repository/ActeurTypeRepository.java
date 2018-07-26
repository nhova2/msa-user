package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.ActeurType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActeurType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeurTypeRepository extends JpaRepository<ActeurType, Long> {

}
