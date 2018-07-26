package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Organisme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Organisme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganismeRepository extends JpaRepository<Organisme, Long> {

}
