package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.AnimateurFournisseur;

import ftg.ps.project.ms.acteur.repository.AnimateurFournisseurRepository;
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
 * REST controller for managing AnimateurFournisseur.
 */
@RestController
@RequestMapping("/api")
public class AnimateurFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(AnimateurFournisseurResource.class);

    private static final String ENTITY_NAME = "animateurFournisseur";

    private final AnimateurFournisseurRepository animateurFournisseurRepository;

    public AnimateurFournisseurResource(AnimateurFournisseurRepository animateurFournisseurRepository) {
        this.animateurFournisseurRepository = animateurFournisseurRepository;
    }

    /**
     * POST  /animateur-fournisseurs : Create a new animateurFournisseur.
     *
     * @param animateurFournisseur the animateurFournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new animateurFournisseur, or with status 400 (Bad Request) if the animateurFournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/animateur-fournisseurs")
    @Timed
    public ResponseEntity<AnimateurFournisseur> createAnimateurFournisseur(@RequestBody AnimateurFournisseur animateurFournisseur) throws URISyntaxException {
        log.debug("REST request to save AnimateurFournisseur : {}", animateurFournisseur);
        if (animateurFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new animateurFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimateurFournisseur result = animateurFournisseurRepository.save(animateurFournisseur);
        return ResponseEntity.created(new URI("/api/animateur-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /animateur-fournisseurs : Updates an existing animateurFournisseur.
     *
     * @param animateurFournisseur the animateurFournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated animateurFournisseur,
     * or with status 400 (Bad Request) if the animateurFournisseur is not valid,
     * or with status 500 (Internal Server Error) if the animateurFournisseur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/animateur-fournisseurs")
    @Timed
    public ResponseEntity<AnimateurFournisseur> updateAnimateurFournisseur(@RequestBody AnimateurFournisseur animateurFournisseur) throws URISyntaxException {
        log.debug("REST request to update AnimateurFournisseur : {}", animateurFournisseur);
        if (animateurFournisseur.getId() == null) {
            return createAnimateurFournisseur(animateurFournisseur);
        }
        AnimateurFournisseur result = animateurFournisseurRepository.save(animateurFournisseur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, animateurFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /animateur-fournisseurs : get all the animateurFournisseurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of animateurFournisseurs in body
     */
    @GetMapping("/animateur-fournisseurs")
    @Timed
    public List<AnimateurFournisseur> getAllAnimateurFournisseurs() {
        log.debug("REST request to get all AnimateurFournisseurs");
        return animateurFournisseurRepository.findAll();
        }

    /**
     * GET  /animateur-fournisseurs/:id : get the "id" animateurFournisseur.
     *
     * @param id the id of the animateurFournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the animateurFournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/animateur-fournisseurs/{id}")
    @Timed
    public ResponseEntity<AnimateurFournisseur> getAnimateurFournisseur(@PathVariable Long id) {
        log.debug("REST request to get AnimateurFournisseur : {}", id);
        AnimateurFournisseur animateurFournisseur = animateurFournisseurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(animateurFournisseur));
    }

    /**
     * DELETE  /animateur-fournisseurs/:id : delete the "id" animateurFournisseur.
     *
     * @param id the id of the animateurFournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/animateur-fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnimateurFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete AnimateurFournisseur : {}", id);
        animateurFournisseurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
