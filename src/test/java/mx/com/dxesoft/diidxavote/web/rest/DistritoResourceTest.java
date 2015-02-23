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
import mx.com.dxesoft.diidxavote.domain.Distrito;
import mx.com.dxesoft.diidxavote.repository.DistritoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DistritoResource REST controller.
 *
 * @see DistritoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DistritoResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private DistritoRepository distritoRepository;

    private MockMvc restDistritoMockMvc;

    private Distrito distrito;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DistritoResource distritoResource = new DistritoResource();
        ReflectionTestUtils.setField(distritoResource, "distritoRepository", distritoRepository);
        this.restDistritoMockMvc = MockMvcBuilders.standaloneSetup(distritoResource).build();
    }

    @Before
    public void initTest() {
        distrito = new Distrito();
        distrito.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createDistrito() throws Exception {
        // Validate the database is empty
        assertThat(distritoRepository.findAll()).hasSize(0);

        // Create the Distrito
        restDistritoMockMvc.perform(post("/api/distritos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(distrito)))
                .andExpect(status().isOk());

        // Validate the Distrito in the database
        List<Distrito> distritos = distritoRepository.findAll();
        assertThat(distritos).hasSize(1);
        Distrito testDistrito = distritos.iterator().next();
        assertThat(testDistrito.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDistritos() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Get all the distritos
        restDistritoMockMvc.perform(get("/api/distritos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(distrito.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Get the distrito
        restDistritoMockMvc.perform(get("/api/distritos/{id}", distrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(distrito.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDistrito() throws Exception {
        // Get the distrito
        restDistritoMockMvc.perform(get("/api/distritos/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Update the distrito
        distrito.setNombre(UPDATED_NOMBRE);
        restDistritoMockMvc.perform(post("/api/distritos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(distrito)))
                .andExpect(status().isOk());

        // Validate the Distrito in the database
        List<Distrito> distritos = distritoRepository.findAll();
        assertThat(distritos).hasSize(1);
        Distrito testDistrito = distritos.iterator().next();
        assertThat(testDistrito.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Get the distrito
        restDistritoMockMvc.perform(delete("/api/distritos/{id}", distrito.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Distrito> distritos = distritoRepository.findAll();
        assertThat(distritos).hasSize(0);
    }
}
