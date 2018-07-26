package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Acheteur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Acheteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcheteurRepository extends JpaRepository<Acheteur, Long> {

}
