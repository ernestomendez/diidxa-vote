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
import mx.com.dxesoft.diidxavote.domain.Estado;
import mx.com.dxesoft.diidxavote.repository.EstadoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstadoResource REST controller.
 *
 * @see EstadoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EstadoResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private EstadoRepository estadoRepository;

    private MockMvc restEstadoMockMvc;

    private Estado estado;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstadoResource estadoResource = new EstadoResource();
        ReflectionTestUtils.setField(estadoResource, "estadoRepository", estadoRepository);
        this.restEstadoMockMvc = MockMvcBuilders.standaloneSetup(estadoResource).build();
    }

    @Before
    public void initTest() {
        estado = new Estado();
        estado.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createEstado() throws Exception {
        // Validate the database is empty
        assertThat(estadoRepository.findAll()).hasSize(0);

        // Create the Estado
        restEstadoMockMvc.perform(post("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(1);
        Estado testEstado = estados.iterator().next();
        assertThat(testEstado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEstados() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get all the estados
        restEstadoMockMvc.perform(get("/api/estados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(estado.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get the estado
        restEstadoMockMvc.perform(get("/api/estados/{id}", estado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(estado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstado() throws Exception {
        // Get the estado
        restEstadoMockMvc.perform(get("/api/estados/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Update the estado
        estado.setNombre(UPDATED_NOMBRE);
        restEstadoMockMvc.perform(post("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(1);
        Estado testEstado = estados.iterator().next();
        assertThat(testEstado.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get the estado
        restEstadoMockMvc.perform(delete("/api/estados/{id}", estado.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(0);
    }
}
