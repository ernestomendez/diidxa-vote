package mx.com.dxesoft.diidxavote.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import mx.com.dxesoft.diidxavote.Application;
import mx.com.dxesoft.diidxavote.domain.Elector;
import mx.com.dxesoft.diidxavote.repository.ElectorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ElectorResource REST controller.
 *
 * @see ElectorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ElectorResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_DIRECCION = "SAMPLE_TEXT";
    private static final String UPDATED_DIRECCION = "UPDATED_TEXT";

    private static final Integer DEFAULT_EDAD = 0;
    private static final Integer UPDATED_EDAD = 1;
    private static final String DEFAULT_GENERO = "SAMPLE_TEXT";
    private static final String UPDATED_GENERO = "UPDATED_TEXT";

    private static final Long DEFAULT_CONSECUTIVO_IFE = 0L;
    private static final Long UPDATED_CONSECUTIVO_IFE = 1L;
    private static final String DEFAULT_ROL = "SAMPLE_TEXT";
    private static final String UPDATED_ROL = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_CELULAR = "SAMPLE_TEXT";
    private static final String UPDATED_CELULAR = "UPDATED_TEXT";
    private static final String DEFAULT_TELEFONO = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFONO = "UPDATED_TEXT";

    @Inject
    private ElectorRepository electorRepository;

    private MockMvc restElectorMockMvc;

    private Elector elector;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElectorResource electorResource = new ElectorResource();
        ReflectionTestUtils.setField(electorResource, "electorRepository", electorRepository);
        this.restElectorMockMvc = MockMvcBuilders.standaloneSetup(electorResource).build();
    }

    @Before
    public void initTest() {
        elector = new Elector();
        elector.setNombre(DEFAULT_NOMBRE);
        elector.setDireccion(DEFAULT_DIRECCION);
        elector.setEdad(DEFAULT_EDAD);
        elector.setGenero(DEFAULT_GENERO);
        elector.setConsecutivoIFE(DEFAULT_CONSECUTIVO_IFE);
        elector.setRol(DEFAULT_ROL);
        elector.setEmail(DEFAULT_EMAIL);
        elector.setCelular(DEFAULT_CELULAR);
        elector.setTelefono(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createElector() throws Exception {
        // Validate the database is empty
        assertThat(electorRepository.findAll()).hasSize(0);

        // Create the Elector
        restElectorMockMvc.perform(post("/api/electors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(elector)))
                .andExpect(status().isOk());

        // Validate the Elector in the database
        List<Elector> electors = electorRepository.findAll();
        assertThat(electors).hasSize(1);
        Elector testElector = electors.iterator().next();
        assertThat(testElector.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testElector.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testElector.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testElector.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testElector.getConsecutivoIFE()).isEqualTo(DEFAULT_CONSECUTIVO_IFE);
        assertThat(testElector.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testElector.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testElector.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testElector.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllElectors() throws Exception {
        // Initialize the database
        electorRepository.saveAndFlush(elector);

        // Get all the electors
        restElectorMockMvc.perform(get("/api/electors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(elector.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()))
                .andExpect(jsonPath("$.[0].direccion").value(DEFAULT_DIRECCION.toString()))
                .andExpect(jsonPath("$.[0].edad").value(DEFAULT_EDAD))
                .andExpect(jsonPath("$.[0].genero").value(DEFAULT_GENERO.toString()))
                .andExpect(jsonPath("$.[0].consecutivoIFE").value(DEFAULT_CONSECUTIVO_IFE.intValue()))
                .andExpect(jsonPath("$.[0].rol").value(DEFAULT_ROL.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].celular").value(DEFAULT_CELULAR.toString()))
                .andExpect(jsonPath("$.[0].telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getElector() throws Exception {
        // Initialize the database
        electorRepository.saveAndFlush(elector);

        // Get the elector
        restElectorMockMvc.perform(get("/api/electors/{id}", elector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(elector.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.consecutivoIFE").value(DEFAULT_CONSECUTIVO_IFE.intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElector() throws Exception {
        // Get the elector
        restElectorMockMvc.perform(get("/api/electors/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElector() throws Exception {
        // Initialize the database
        electorRepository.saveAndFlush(elector);

        // Update the elector
        elector.setNombre(UPDATED_NOMBRE);
        elector.setDireccion(UPDATED_DIRECCION);
        elector.setEdad(UPDATED_EDAD);
        elector.setGenero(UPDATED_GENERO);
        elector.setConsecutivoIFE(UPDATED_CONSECUTIVO_IFE);
        elector.setRol(UPDATED_ROL);
        elector.setEmail(UPDATED_EMAIL);
        elector.setCelular(UPDATED_CELULAR);
        elector.setTelefono(UPDATED_TELEFONO);
        restElectorMockMvc.perform(post("/api/electors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(elector)))
                .andExpect(status().isOk());

        // Validate the Elector in the database
        List<Elector> electors = electorRepository.findAll();
        assertThat(electors).hasSize(1);
        Elector testElector = electors.iterator().next();
        assertThat(testElector.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testElector.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testElector.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testElector.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testElector.getConsecutivoIFE()).isEqualTo(UPDATED_CONSECUTIVO_IFE);
        assertThat(testElector.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testElector.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testElector.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testElector.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void deleteElector() throws Exception {
        // Initialize the database
        electorRepository.saveAndFlush(elector);

        // Get the elector
        restElectorMockMvc.perform(delete("/api/electors/{id}", elector.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Elector> electors = electorRepository.findAll();
        assertThat(electors).hasSize(0);
    }
}
