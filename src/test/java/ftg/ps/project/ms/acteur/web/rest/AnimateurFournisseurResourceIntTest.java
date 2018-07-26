package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.acteur.domain.AnimateurFournisseur;
import ftg.ps.project.ms.acteur.repository.AnimateurFournisseurRepository;
import ftg.ps.project.ms.acteur.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ftg.ps.project.ms.acteur.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnimateurFournisseurResource REST controller.
 *
 * @see AnimateurFournisseurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ActeurApp.class, SecurityBeanOverrideConfiguration.class})
public class AnimateurFournisseurResourceIntTest {

    private static final Integer DEFAULT_NIVEAU_AGREEMENT = 1;
    private static final Integer UPDATED_NIVEAU_AGREEMENT = 2;

    @Autowired
    private AnimateurFournisseurRepository animateurFournisseurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnimateurFournisseurMockMvc;

    private AnimateurFournisseur animateurFournisseur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimateurFournisseurResource animateurFournisseurResource = new AnimateurFournisseurResource(animateurFournisseurRepository);
        this.restAnimateurFournisseurMockMvc = MockMvcBuilders.standaloneSetup(animateurFournisseurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimateurFournisseur createEntity(EntityManager em) {
        AnimateurFournisseur animateurFournisseur = new AnimateurFournisseur()
            .niveauAgreement(DEFAULT_NIVEAU_AGREEMENT);
        return animateurFournisseur;
    }

    @Before
    public void initTest() {
        animateurFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimateurFournisseur() throws Exception {
        int databaseSizeBeforeCreate = animateurFournisseurRepository.findAll().size();

        // Create the AnimateurFournisseur
        restAnimateurFournisseurMockMvc.perform(post("/api/animateur-fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateurFournisseur)))
            .andExpect(status().isCreated());

        // Validate the AnimateurFournisseur in the database
        List<AnimateurFournisseur> animateurFournisseurList = animateurFournisseurRepository.findAll();
        assertThat(animateurFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        AnimateurFournisseur testAnimateurFournisseur = animateurFournisseurList.get(animateurFournisseurList.size() - 1);
        assertThat(testAnimateurFournisseur.getNiveauAgreement()).isEqualTo(DEFAULT_NIVEAU_AGREEMENT);
    }

    @Test
    @Transactional
    public void createAnimateurFournisseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animateurFournisseurRepository.findAll().size();

        // Create the AnimateurFournisseur with an existing ID
        animateurFournisseur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimateurFournisseurMockMvc.perform(post("/api/animateur-fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateurFournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the AnimateurFournisseur in the database
        List<AnimateurFournisseur> animateurFournisseurList = animateurFournisseurRepository.findAll();
        assertThat(animateurFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnimateurFournisseurs() throws Exception {
        // Initialize the database
        animateurFournisseurRepository.saveAndFlush(animateurFournisseur);

        // Get all the animateurFournisseurList
        restAnimateurFournisseurMockMvc.perform(get("/api/animateur-fournisseurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animateurFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveauAgreement").value(hasItem(DEFAULT_NIVEAU_AGREEMENT)));
    }

    @Test
    @Transactional
    public void getAnimateurFournisseur() throws Exception {
        // Initialize the database
        animateurFournisseurRepository.saveAndFlush(animateurFournisseur);

        // Get the animateurFournisseur
        restAnimateurFournisseurMockMvc.perform(get("/api/animateur-fournisseurs/{id}", animateurFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animateurFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.niveauAgreement").value(DEFAULT_NIVEAU_AGREEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingAnimateurFournisseur() throws Exception {
        // Get the animateurFournisseur
        restAnimateurFournisseurMockMvc.perform(get("/api/animateur-fournisseurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimateurFournisseur() throws Exception {
        // Initialize the database
        animateurFournisseurRepository.saveAndFlush(animateurFournisseur);
        int databaseSizeBeforeUpdate = animateurFournisseurRepository.findAll().size();

        // Update the animateurFournisseur
        AnimateurFournisseur updatedAnimateurFournisseur = animateurFournisseurRepository.findOne(animateurFournisseur.getId());
        // Disconnect from session so that the updates on updatedAnimateurFournisseur are not directly saved in db
        em.detach(updatedAnimateurFournisseur);
        updatedAnimateurFournisseur
            .niveauAgreement(UPDATED_NIVEAU_AGREEMENT);

        restAnimateurFournisseurMockMvc.perform(put("/api/animateur-fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimateurFournisseur)))
            .andExpect(status().isOk());

        // Validate the AnimateurFournisseur in the database
        List<AnimateurFournisseur> animateurFournisseurList = animateurFournisseurRepository.findAll();
        assertThat(animateurFournisseurList).hasSize(databaseSizeBeforeUpdate);
        AnimateurFournisseur testAnimateurFournisseur = animateurFournisseurList.get(animateurFournisseurList.size() - 1);
        assertThat(testAnimateurFournisseur.getNiveauAgreement()).isEqualTo(UPDATED_NIVEAU_AGREEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimateurFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = animateurFournisseurRepository.findAll().size();

        // Create the AnimateurFournisseur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnimateurFournisseurMockMvc.perform(put("/api/animateur-fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateurFournisseur)))
            .andExpect(status().isCreated());

        // Validate the AnimateurFournisseur in the database
        List<AnimateurFournisseur> animateurFournisseurList = animateurFournisseurRepository.findAll();
        assertThat(animateurFournisseurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnimateurFournisseur() throws Exception {
        // Initialize the database
        animateurFournisseurRepository.saveAndFlush(animateurFournisseur);
        int databaseSizeBeforeDelete = animateurFournisseurRepository.findAll().size();

        // Get the animateurFournisseur
        restAnimateurFournisseurMockMvc.perform(delete("/api/animateur-fournisseurs/{id}", animateurFournisseur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnimateurFournisseur> animateurFournisseurList = animateurFournisseurRepository.findAll();
        assertThat(animateurFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimateurFournisseur.class);
        AnimateurFournisseur animateurFournisseur1 = new AnimateurFournisseur();
        animateurFournisseur1.setId(1L);
        AnimateurFournisseur animateurFournisseur2 = new AnimateurFournisseur();
        animateurFournisseur2.setId(animateurFournisseur1.getId());
        assertThat(animateurFournisseur1).isEqualTo(animateurFournisseur2);
        animateurFournisseur2.setId(2L);
        assertThat(animateurFournisseur1).isNotEqualTo(animateurFournisseur2);
        animateurFournisseur1.setId(null);
        assertThat(animateurFournisseur1).isNotEqualTo(animateurFournisseur2);
    }
}
