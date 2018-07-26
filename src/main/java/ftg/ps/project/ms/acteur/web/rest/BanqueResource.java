package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Banque;

import ftg.ps.project.ms.acteur.repository.BanqueRepository;
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
 * REST controller for managing Banque.
 */
@RestController
@RequestMapping("/api")
public class BanqueResource {

    private final Logger log = LoggerFactory.getLogger(BanqueResource.class);

    private static final String ENTITY_NAME = "banque";

    private final BanqueRepository banqueRepository;

    public BanqueResource(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    /**
     * POST  /banques : Create a new banque.
     *
     * @param banque the banque to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banque, or with status 400 (Bad Request) if the banque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/banques")
    @Timed
    public ResponseEntity<Banque> createBanque(@RequestBody Banque banque) throws URISyntaxException {
        log.debug("REST request to save Banque : {}", banque);
        if (banque.getId() != null) {
            throw new BadRequestAlertException("A new banque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Banque result = banqueRepository.save(banque);
        return ResponseEntity.created(new URI("/api/banques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /banques : Updates an existing banque.
     *
     * @param banque the banque to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banque,
     * or with status 400 (Bad Request) if the banque is not valid,
     * or with status 500 (Internal Server Error) if the banque couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/banques")
    @Timed
    public ResponseEntity<Banque> updateBanque(@RequestBody Banque banque) throws URISyntaxException {
        log.debug("REST request to update Banque : {}", banque);
        if (banque.getId() == null) {
            return createBanque(banque);
        }
        Banque result = banqueRepository.save(banque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, banque.getId().toString()))
            .body(result);
    }

    /**
     * GET  /banques : get all the banques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of banques in body
     */
    @GetMapping("/banques")
    @Timed
    public List<Banque> getAllBanques() {
        log.debug("REST request to get all Banques");
        return banqueRepository.findAll();
        }

    /**
     * GET  /banques/:id : get the "id" banque.
     *
     * @param id the id of the banque to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banque, or with status 404 (Not Found)
     */
    @GetMapping("/banques/{id}")
    @Timed
    public ResponseEntity<Banque> getBanque(@PathVariable Long id) {
        log.debug("REST request to get Banque : {}", id);
        Banque banque = banqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(banque));
    }

    /**
     * DELETE  /banques/:id : delete the "id" banque.
     *
     * @param id the id of the banque to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/banques/{id}")
    @Timed
    public ResponseEntity<Void> deleteBanque(@PathVariable Long id) {
        log.debug("REST request to delete Banque : {}", id);
        banqueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
