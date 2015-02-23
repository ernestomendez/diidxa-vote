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
import mx.com.dxesoft.diidxavote.domain.Seccion;
import mx.com.dxesoft.diidxavote.repository.SeccionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeccionResource REST controller.
 *
 * @see SeccionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SeccionResourceTest {

    private static final String DEFAULT_CASILLA = "SAMPLE_TEXT";
    private static final String UPDATED_CASILLA = "UPDATED_TEXT";

    @Inject
    private SeccionRepository seccionRepository;

    private MockMvc restSeccionMockMvc;

    private Seccion seccion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeccionResource seccionResource = new SeccionResource();
        ReflectionTestUtils.setField(seccionResource, "seccionRepository", seccionRepository);
        this.restSeccionMockMvc = MockMvcBuilders.standaloneSetup(seccionResource).build();
    }

    @Before
    public void initTest() {
        seccion = new Seccion();
        seccion.setCasilla(DEFAULT_CASILLA);
    }

    @Test
    @Transactional
    public void createSeccion() throws Exception {
        // Validate the database is empty
        assertThat(seccionRepository.findAll()).hasSize(0);

        // Create the Seccion
        restSeccionMockMvc.perform(post("/api/seccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seccion)))
                .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccions = seccionRepository.findAll();
        assertThat(seccions).hasSize(1);
        Seccion testSeccion = seccions.iterator().next();
        assertThat(testSeccion.getCasilla()).isEqualTo(DEFAULT_CASILLA);
    }

    @Test
    @Transactional
    public void getAllSeccions() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get all the seccions
        restSeccionMockMvc.perform(get("/api/seccions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(seccion.getId().intValue()))
                .andExpect(jsonPath("$.[0].casilla").value(DEFAULT_CASILLA.toString()));
    }

    @Test
    @Transactional
    public void getSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", seccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(seccion.getId().intValue()))
            .andExpect(jsonPath("$.casilla").value(DEFAULT_CASILLA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeccion() throws Exception {
        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Update the seccion
        seccion.setCasilla(UPDATED_CASILLA);
        restSeccionMockMvc.perform(post("/api/seccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seccion)))
                .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccions = seccionRepository.findAll();
        assertThat(seccions).hasSize(1);
        Seccion testSeccion = seccions.iterator().next();
        assertThat(testSeccion.getCasilla()).isEqualTo(UPDATED_CASILLA);
    }

    @Test
    @Transactional
    public void deleteSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get the seccion
        restSeccionMockMvc.perform(delete("/api/seccions/{id}", seccion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Seccion> seccions = seccionRepository.findAll();
        assertThat(seccions).hasSize(0);
    }
}
