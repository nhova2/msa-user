package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Banque;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Banque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {

}
