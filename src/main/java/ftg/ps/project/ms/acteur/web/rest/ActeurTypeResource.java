package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.ActeurType;

import ftg.ps.project.ms.acteur.repository.ActeurTypeRepository;
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
 * REST controller for managing ActeurType.
 */
@RestController
@RequestMapping("/api")
public class ActeurTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActeurTypeResource.class);

    private static final String ENTITY_NAME = "acteurType";

    private final ActeurTypeRepository acteurTypeRepository;

    public ActeurTypeResource(ActeurTypeRepository acteurTypeRepository) {
        this.acteurTypeRepository = acteurTypeRepository;
    }

    /**
     * POST  /acteur-types : Create a new acteurType.
     *
     * @param acteurType the acteurType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acteurType, or with status 400 (Bad Request) if the acteurType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acteur-types")
    @Timed
    public ResponseEntity<ActeurType> createActeurType(@RequestBody ActeurType acteurType) throws URISyntaxException {
        log.debug("REST request to save ActeurType : {}", acteurType);
        if (acteurType.getId() != null) {
            throw new BadRequestAlertException("A new acteurType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActeurType result = acteurTypeRepository.save(acteurType);
        return ResponseEntity.created(new URI("/api/acteur-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acteur-types : Updates an existing acteurType.
     *
     * @param acteurType the acteurType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acteurType,
     * or with status 400 (Bad Request) if the acteurType is not valid,
     * or with status 500 (Internal Server Error) if the acteurType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acteur-types")
    @Timed
    public ResponseEntity<ActeurType> updateActeurType(@RequestBody ActeurType acteurType) throws URISyntaxException {
        log.debug("REST request to update ActeurType : {}", acteurType);
        if (acteurType.getId() == null) {
            return createActeurType(acteurType);
        }
        ActeurType result = acteurTypeRepository.save(acteurType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acteurType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acteur-types : get all the acteurTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acteurTypes in body
     */
    @GetMapping("/acteur-types")
    @Timed
    public List<ActeurType> getAllActeurTypes() {
        log.debug("REST request to get all ActeurTypes");
        return acteurTypeRepository.findAll();
        }

    /**
     * GET  /acteur-types/:id : get the "id" acteurType.
     *
     * @param id the id of the acteurType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acteurType, or with status 404 (Not Found)
     */
    @GetMapping("/acteur-types/{id}")
    @Timed
    public ResponseEntity<ActeurType> getActeurType(@PathVariable Long id) {
        log.debug("REST request to get ActeurType : {}", id);
        ActeurType acteurType = acteurTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acteurType));
    }

    /**
     * DELETE  /acteur-types/:id : delete the "id" acteurType.
     *
     * @param id the id of the acteurType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acteur-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteActeurType(@PathVariable Long id) {
        log.debug("REST request to delete ActeurType : {}", id);
        acteurTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
