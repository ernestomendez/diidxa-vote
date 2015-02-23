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
import mx.com.dxesoft.diidxavote.domain.Ruta;
import mx.com.dxesoft.diidxavote.repository.RutaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RutaResource REST controller.
 *
 * @see RutaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RutaResourceTest {

    private static final String DEFAULT_DESCRIPCION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPCION = "UPDATED_TEXT";

    @Inject
    private RutaRepository rutaRepository;

    private MockMvc restRutaMockMvc;

    private Ruta ruta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RutaResource rutaResource = new RutaResource();
        ReflectionTestUtils.setField(rutaResource, "rutaRepository", rutaRepository);
        this.restRutaMockMvc = MockMvcBuilders.standaloneSetup(rutaResource).build();
    }

    @Before
    public void initTest() {
        ruta = new Ruta();
        ruta.setDescripcion(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createRuta() throws Exception {
        // Validate the database is empty
        assertThat(rutaRepository.findAll()).hasSize(0);

        // Create the Ruta
        restRutaMockMvc.perform(post("/api/rutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ruta)))
                .andExpect(status().isOk());

        // Validate the Ruta in the database
        List<Ruta> rutas = rutaRepository.findAll();
        assertThat(rutas).hasSize(1);
        Ruta testRuta = rutas.iterator().next();
        assertThat(testRuta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllRutas() throws Exception {
        // Initialize the database
        rutaRepository.saveAndFlush(ruta);

        // Get all the rutas
        restRutaMockMvc.perform(get("/api/rutas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(ruta.getId().intValue()))
                .andExpect(jsonPath("$.[0].descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getRuta() throws Exception {
        // Initialize the database
        rutaRepository.saveAndFlush(ruta);

        // Get the ruta
        restRutaMockMvc.perform(get("/api/rutas/{id}", ruta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ruta.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRuta() throws Exception {
        // Get the ruta
        restRutaMockMvc.perform(get("/api/rutas/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuta() throws Exception {
        // Initialize the database
        rutaRepository.saveAndFlush(ruta);

        // Update the ruta
        ruta.setDescripcion(UPDATED_DESCRIPCION);
        restRutaMockMvc.perform(post("/api/rutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ruta)))
                .andExpect(status().isOk());

        // Validate the Ruta in the database
        List<Ruta> rutas = rutaRepository.findAll();
        assertThat(rutas).hasSize(1);
        Ruta testRuta = rutas.iterator().next();
        assertThat(testRuta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void deleteRuta() throws Exception {
        // Initialize the database
        rutaRepository.saveAndFlush(ruta);

        // Get the ruta
        restRutaMockMvc.perform(delete("/api/rutas/{id}", ruta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ruta> rutas = rutaRepository.findAll();
        assertThat(rutas).hasSize(0);
    }
}
