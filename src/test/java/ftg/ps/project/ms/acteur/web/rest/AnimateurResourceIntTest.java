package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.acteur.domain.Animateur;
import ftg.ps.project.ms.acteur.repository.AnimateurRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ftg.ps.project.ms.acteur.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnimateurResource REST controller.
 *
 * @see AnimateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ActeurApp.class, SecurityBeanOverrideConfiguration.class})
public class AnimateurResourceIntTest {

    private static final Long DEFAULT_ANIMATEUR_ID = 1L;
    private static final Long UPDATED_ANIMATEUR_ID = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AnimateurRepository animateurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnimateurMockMvc;

    private Animateur animateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimateurResource animateurResource = new AnimateurResource(animateurRepository);
        this.restAnimateurMockMvc = MockMvcBuilders.standaloneSetup(animateurResource)
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
    public static Animateur createEntity(EntityManager em) {
        Animateur animateur = new Animateur()
            .animateurId(DEFAULT_ANIMATEUR_ID)
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return animateur;
    }

    @Before
    public void initTest() {
        animateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimateur() throws Exception {
        int databaseSizeBeforeCreate = animateurRepository.findAll().size();

        // Create the Animateur
        restAnimateurMockMvc.perform(post("/api/animateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateur)))
            .andExpect(status().isCreated());

        // Validate the Animateur in the database
        List<Animateur> animateurList = animateurRepository.findAll();
        assertThat(animateurList).hasSize(databaseSizeBeforeCreate + 1);
        Animateur testAnimateur = animateurList.get(animateurList.size() - 1);
        assertThat(testAnimateur.getAnimateurId()).isEqualTo(DEFAULT_ANIMATEUR_ID);
        assertThat(testAnimateur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAnimateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAnimateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAnimateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAnimateur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAnimateur.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAnimateur.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testAnimateur.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAnimateur.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);
    }

    @Test
    @Transactional
    public void createAnimateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animateurRepository.findAll().size();

        // Create the Animateur with an existing ID
        animateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimateurMockMvc.perform(post("/api/animateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateur)))
            .andExpect(status().isBadRequest());

        // Validate the Animateur in the database
        List<Animateur> animateurList = animateurRepository.findAll();
        assertThat(animateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnimateurs() throws Exception {
        // Initialize the database
        animateurRepository.saveAndFlush(animateur);

        // Get all the animateurList
        restAnimateurMockMvc.perform(get("/api/animateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].animateurId").value(hasItem(DEFAULT_ANIMATEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }

    @Test
    @Transactional
    public void getAnimateur() throws Exception {
        // Initialize the database
        animateurRepository.saveAndFlush(animateur);

        // Get the animateur
        restAnimateurMockMvc.perform(get("/api/animateurs/{id}", animateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animateur.getId().intValue()))
            .andExpect(jsonPath("$.animateurId").value(DEFAULT_ANIMATEUR_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimateur() throws Exception {
        // Get the animateur
        restAnimateurMockMvc.perform(get("/api/animateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimateur() throws Exception {
        // Initialize the database
        animateurRepository.saveAndFlush(animateur);
        int databaseSizeBeforeUpdate = animateurRepository.findAll().size();

        // Update the animateur
        Animateur updatedAnimateur = animateurRepository.findOne(animateur.getId());
        // Disconnect from session so that the updates on updatedAnimateur are not directly saved in db
        em.detach(updatedAnimateur);
        updatedAnimateur
            .animateurId(UPDATED_ANIMATEUR_ID)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restAnimateurMockMvc.perform(put("/api/animateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimateur)))
            .andExpect(status().isOk());

        // Validate the Animateur in the database
        List<Animateur> animateurList = animateurRepository.findAll();
        assertThat(animateurList).hasSize(databaseSizeBeforeUpdate);
        Animateur testAnimateur = animateurList.get(animateurList.size() - 1);
        assertThat(testAnimateur.getAnimateurId()).isEqualTo(UPDATED_ANIMATEUR_ID);
        assertThat(testAnimateur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAnimateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAnimateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAnimateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAnimateur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAnimateur.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAnimateur.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testAnimateur.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAnimateur.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimateur() throws Exception {
        int databaseSizeBeforeUpdate = animateurRepository.findAll().size();

        // Create the Animateur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnimateurMockMvc.perform(put("/api/animateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animateur)))
            .andExpect(status().isCreated());

        // Validate the Animateur in the database
        List<Animateur> animateurList = animateurRepository.findAll();
        assertThat(animateurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnimateur() throws Exception {
        // Initialize the database
        animateurRepository.saveAndFlush(animateur);
        int databaseSizeBeforeDelete = animateurRepository.findAll().size();

        // Get the animateur
        restAnimateurMockMvc.perform(delete("/api/animateurs/{id}", animateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Animateur> animateurList = animateurRepository.findAll();
        assertThat(animateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Animateur.class);
        Animateur animateur1 = new Animateur();
        animateur1.setId(1L);
        Animateur animateur2 = new Animateur();
        animateur2.setId(animateur1.getId());
        assertThat(animateur1).isEqualTo(animateur2);
        animateur2.setId(2L);
        assertThat(animateur1).isNotEqualTo(animateur2);
        animateur1.setId(null);
        assertThat(animateur1).isNotEqualTo(animateur2);
    }
}
