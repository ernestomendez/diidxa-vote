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
import mx.com.dxesoft.diidxavote.domain.PartidosPoliticos;
import mx.com.dxesoft.diidxavote.repository.PartidosPoliticosRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartidosPoliticosResource REST controller.
 *
 * @see PartidosPoliticosResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartidosPoliticosResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private PartidosPoliticosRepository partidosPoliticosRepository;

    private MockMvc restPartidosPoliticosMockMvc;

    private PartidosPoliticos partidosPoliticos;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartidosPoliticosResource partidosPoliticosResource = new PartidosPoliticosResource();
        ReflectionTestUtils.setField(partidosPoliticosResource, "partidosPoliticosRepository", partidosPoliticosRepository);
        this.restPartidosPoliticosMockMvc = MockMvcBuilders.standaloneSetup(partidosPoliticosResource).build();
    }

    @Before
    public void initTest() {
        partidosPoliticos = new PartidosPoliticos();
        partidosPoliticos.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPartidosPoliticos() throws Exception {
        // Validate the database is empty
        assertThat(partidosPoliticosRepository.findAll()).hasSize(0);

        // Create the PartidosPoliticos
        restPartidosPoliticosMockMvc.perform(post("/api/partidosPoliticoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partidosPoliticos)))
                .andExpect(status().isOk());

        // Validate the PartidosPoliticos in the database
        List<PartidosPoliticos> partidosPoliticoss = partidosPoliticosRepository.findAll();
        assertThat(partidosPoliticoss).hasSize(1);
        PartidosPoliticos testPartidosPoliticos = partidosPoliticoss.iterator().next();
        assertThat(testPartidosPoliticos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPartidosPoliticoss() throws Exception {
        // Initialize the database
        partidosPoliticosRepository.saveAndFlush(partidosPoliticos);

        // Get all the partidosPoliticoss
        restPartidosPoliticosMockMvc.perform(get("/api/partidosPoliticoss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(partidosPoliticos.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getPartidosPoliticos() throws Exception {
        // Initialize the database
        partidosPoliticosRepository.saveAndFlush(partidosPoliticos);

        // Get the partidosPoliticos
        restPartidosPoliticosMockMvc.perform(get("/api/partidosPoliticoss/{id}", partidosPoliticos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partidosPoliticos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartidosPoliticos() throws Exception {
        // Get the partidosPoliticos
        restPartidosPoliticosMockMvc.perform(get("/api/partidosPoliticoss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartidosPoliticos() throws Exception {
        // Initialize the database
        partidosPoliticosRepository.saveAndFlush(partidosPoliticos);

        // Update the partidosPoliticos
        partidosPoliticos.setNombre(UPDATED_NOMBRE);
        restPartidosPoliticosMockMvc.perform(post("/api/partidosPoliticoss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partidosPoliticos)))
                .andExpect(status().isOk());

        // Validate the PartidosPoliticos in the database
        List<PartidosPoliticos> partidosPoliticoss = partidosPoliticosRepository.findAll();
        assertThat(partidosPoliticoss).hasSize(1);
        PartidosPoliticos testPartidosPoliticos = partidosPoliticoss.iterator().next();
        assertThat(testPartidosPoliticos.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deletePartidosPoliticos() throws Exception {
        // Initialize the database
        partidosPoliticosRepository.saveAndFlush(partidosPoliticos);

        // Get the partidosPoliticos
        restPartidosPoliticosMockMvc.perform(delete("/api/partidosPoliticoss/{id}", partidosPoliticos.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PartidosPoliticos> partidosPoliticoss = partidosPoliticosRepository.findAll();
        assertThat(partidosPoliticoss).hasSize(0);
    }
}
