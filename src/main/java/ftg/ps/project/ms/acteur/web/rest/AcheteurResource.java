package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Acheteur;

import ftg.ps.project.ms.acteur.repository.AcheteurRepository;
import ftg.ps.project.ms.acteur.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.acteur.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Acheteur.
 */
@RestController
@RequestMapping("/api")
public class AcheteurResource {

    private final Logger log = LoggerFactory.getLogger(AcheteurResource.class);

    private static final String ENTITY_NAME = "acheteur";

    private final AcheteurRepository acheteurRepository;

    public AcheteurResource(AcheteurRepository acheteurRepository) {
        this.acheteurRepository = acheteurRepository;
    }

    /**
     * POST  /acheteurs : Create a new acheteur.
     *
     * @param acheteur the acheteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acheteur, or with status 400 (Bad Request) if the acheteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acheteurs")
    @Timed
    public ResponseEntity<Acheteur> createAcheteur(@RequestBody Acheteur acheteur) throws URISyntaxException {
        log.debug("REST request to save Acheteur : {}", acheteur);
        if (acheteur.getId() != null) {
            throw new BadRequestAlertException("A new acheteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acheteur result = acheteurRepository.save(acheteur);
        return ResponseEntity.created(new URI("/api/acheteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acheteurs : Updates an existing acheteur.
     *
     * @param acheteur the acheteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acheteur,
     * or with status 400 (Bad Request) if the acheteur is not valid,
     * or with status 500 (Internal Server Error) if the acheteur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acheteurs")
    @Timed
    public ResponseEntity<Acheteur> updateAcheteur(@RequestBody Acheteur acheteur) throws URISyntaxException {
        log.debug("REST request to update Acheteur : {}", acheteur);
        if (acheteur.getId() == null) {
            return createAcheteur(acheteur);
        }
        Acheteur result = acheteurRepository.save(acheteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acheteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acheteurs : get all the acheteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acheteurs in body
     */
    @GetMapping("/acheteurs")
    @Timed
    public List<Acheteur> getAllAcheteurs() {
        log.debug("REST request to get all Acheteurs");
        return acheteurRepository.findAll();
        }

    /**
     * GET  /acheteurs/:id : get the "id" acheteur.
     *
     * @param id the id of the acheteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acheteur, or with status 404 (Not Found)
     */
    @GetMapping("/acheteurs/{id}")
    @Timed
    public ResponseEntity<Acheteur> getAcheteur(@PathVariable Long id) {
        log.debug("REST request to get Acheteur : {}", id);
        Acheteur acheteur = acheteurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acheteur));
    }

    /**
     * DELETE  /acheteurs/:id : delete the "id" acheteur.
     *
     * @param id the id of the acheteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acheteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcheteur(@PathVariable Long id) {
        log.debug("REST request to delete Acheteur : {}", id);
        acheteurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
