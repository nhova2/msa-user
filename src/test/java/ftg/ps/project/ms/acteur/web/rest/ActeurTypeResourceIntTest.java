package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.acteur.domain.ActeurType;
import ftg.ps.project.ms.acteur.repository.ActeurTypeRepository;
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
 * Test class for the ActeurTypeResource REST controller.
 *
 * @see ActeurTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ActeurApp.class, SecurityBeanOverrideConfiguration.class})
public class ActeurTypeResourceIntTest {

    private static final Long DEFAULT_ID_ACTEUR_TYPE = 1L;
    private static final Long UPDATED_ID_ACTEUR_TYPE = 2L;

    private static final String DEFAULT_LIBELLE_ACTEUR = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ACTEUR = "BBBBBBBBBB";

    @Autowired
    private ActeurTypeRepository acteurTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActeurTypeMockMvc;

    private ActeurType acteurType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeurTypeResource acteurTypeResource = new ActeurTypeResource(acteurTypeRepository);
        this.restActeurTypeMockMvc = MockMvcBuilders.standaloneSetup(acteurTypeResource)
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
    public static ActeurType createEntity(EntityManager em) {
        ActeurType acteurType = new ActeurType()
            .idActeurType(DEFAULT_ID_ACTEUR_TYPE)
            .libelleActeur(DEFAULT_LIBELLE_ACTEUR);
        return acteurType;
    }

    @Before
    public void initTest() {
        acteurType = createEntity(em);
    }

    @Test
    @Transactional
    public void createActeurType() throws Exception {
        int databaseSizeBeforeCreate = acteurTypeRepository.findAll().size();

        // Create the ActeurType
        restActeurTypeMockMvc.perform(post("/api/acteur-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurType)))
            .andExpect(status().isCreated());

        // Validate the ActeurType in the database
        List<ActeurType> acteurTypeList = acteurTypeRepository.findAll();
        assertThat(acteurTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ActeurType testActeurType = acteurTypeList.get(acteurTypeList.size() - 1);
        assertThat(testActeurType.getIdActeurType()).isEqualTo(DEFAULT_ID_ACTEUR_TYPE);
        assertThat(testActeurType.getLibelleActeur()).isEqualTo(DEFAULT_LIBELLE_ACTEUR);
    }

    @Test
    @Transactional
    public void createActeurTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteurTypeRepository.findAll().size();

        // Create the ActeurType with an existing ID
        acteurType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeurTypeMockMvc.perform(post("/api/acteur-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurType)))
            .andExpect(status().isBadRequest());

        // Validate the ActeurType in the database
        List<ActeurType> acteurTypeList = acteurTypeRepository.findAll();
        assertThat(acteurTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActeurTypes() throws Exception {
        // Initialize the database
        acteurTypeRepository.saveAndFlush(acteurType);

        // Get all the acteurTypeList
        restActeurTypeMockMvc.perform(get("/api/acteur-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acteurType.getId().intValue())))
            .andExpect(jsonPath("$.[*].idActeurType").value(hasItem(DEFAULT_ID_ACTEUR_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].libelleActeur").value(hasItem(DEFAULT_LIBELLE_ACTEUR.toString())));
    }

    @Test
    @Transactional
    public void getActeurType() throws Exception {
        // Initialize the database
        acteurTypeRepository.saveAndFlush(acteurType);

        // Get the acteurType
        restActeurTypeMockMvc.perform(get("/api/acteur-types/{id}", acteurType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acteurType.getId().intValue()))
            .andExpect(jsonPath("$.idActeurType").value(DEFAULT_ID_ACTEUR_TYPE.intValue()))
            .andExpect(jsonPath("$.libelleActeur").value(DEFAULT_LIBELLE_ACTEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActeurType() throws Exception {
        // Get the acteurType
        restActeurTypeMockMvc.perform(get("/api/acteur-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActeurType() throws Exception {
        // Initialize the database
        acteurTypeRepository.saveAndFlush(acteurType);
        int databaseSizeBeforeUpdate = acteurTypeRepository.findAll().size();

        // Update the acteurType
        ActeurType updatedActeurType = acteurTypeRepository.findOne(acteurType.getId());
        // Disconnect from session so that the updates on updatedActeurType are not directly saved in db
        em.detach(updatedActeurType);
        updatedActeurType
            .idActeurType(UPDATED_ID_ACTEUR_TYPE)
            .libelleActeur(UPDATED_LIBELLE_ACTEUR);

        restActeurTypeMockMvc.perform(put("/api/acteur-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActeurType)))
            .andExpect(status().isOk());

        // Validate the ActeurType in the database
        List<ActeurType> acteurTypeList = acteurTypeRepository.findAll();
        assertThat(acteurTypeList).hasSize(databaseSizeBeforeUpdate);
        ActeurType testActeurType = acteurTypeList.get(acteurTypeList.size() - 1);
        assertThat(testActeurType.getIdActeurType()).isEqualTo(UPDATED_ID_ACTEUR_TYPE);
        assertThat(testActeurType.getLibelleActeur()).isEqualTo(UPDATED_LIBELLE_ACTEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingActeurType() throws Exception {
        int databaseSizeBeforeUpdate = acteurTypeRepository.findAll().size();

        // Create the ActeurType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActeurTypeMockMvc.perform(put("/api/acteur-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurType)))
            .andExpect(status().isCreated());

        // Validate the ActeurType in the database
        List<ActeurType> acteurTypeList = acteurTypeRepository.findAll();
        assertThat(acteurTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActeurType() throws Exception {
        // Initialize the database
        acteurTypeRepository.saveAndFlush(acteurType);
        int databaseSizeBeforeDelete = acteurTypeRepository.findAll().size();

        // Get the acteurType
        restActeurTypeMockMvc.perform(delete("/api/acteur-types/{id}", acteurType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActeurType> acteurTypeList = acteurTypeRepository.findAll();
        assertThat(acteurTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeurType.class);
        ActeurType acteurType1 = new ActeurType();
        acteurType1.setId(1L);
        ActeurType acteurType2 = new ActeurType();
        acteurType2.setId(acteurType1.getId());
        assertThat(acteurType1).isEqualTo(acteurType2);
        acteurType2.setId(2L);
        assertThat(acteurType1).isNotEqualTo(acteurType2);
        acteurType1.setId(null);
        assertThat(acteurType1).isNotEqualTo(acteurType2);
    }
}
