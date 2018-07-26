package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.AnimateurFournisseur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnimateurFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimateurFournisseurRepository extends JpaRepository<AnimateurFournisseur, Long> {

}
