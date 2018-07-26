package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Animateur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Animateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimateurRepository extends JpaRepository<Animateur, Long> {

}
