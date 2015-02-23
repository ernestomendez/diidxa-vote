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
import mx.com.dxesoft.diidxavote.domain.Municipio;
import mx.com.dxesoft.diidxavote.repository.MunicipioRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MunicipioResource REST controller.
 *
 * @see MunicipioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MunicipioResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_CABECERA_MUNICIPAL = "SAMPLE_TEXT";
    private static final String UPDATED_CABECERA_MUNICIPAL = "UPDATED_TEXT";

    @Inject
    private MunicipioRepository municipioRepository;

    private MockMvc restMunicipioMockMvc;

    private Municipio municipio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MunicipioResource municipioResource = new MunicipioResource();
        ReflectionTestUtils.setField(municipioResource, "municipioRepository", municipioRepository);
        this.restMunicipioMockMvc = MockMvcBuilders.standaloneSetup(municipioResource).build();
    }

    @Before
    public void initTest() {
        municipio = new Municipio();
        municipio.setNombre(DEFAULT_NOMBRE);
        municipio.setCabeceraMunicipal(DEFAULT_CABECERA_MUNICIPAL);
    }

    @Test
    @Transactional
    public void createMunicipio() throws Exception {
        // Validate the database is empty
        assertThat(municipioRepository.findAll()).hasSize(0);

        // Create the Municipio
        restMunicipioMockMvc.perform(post("/api/municipios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(municipio)))
                .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipios = municipioRepository.findAll();
        assertThat(municipios).hasSize(1);
        Municipio testMunicipio = municipios.iterator().next();
        assertThat(testMunicipio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMunicipio.getCabeceraMunicipal()).isEqualTo(DEFAULT_CABECERA_MUNICIPAL);
    }

    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipios
        restMunicipioMockMvc.perform(get("/api/municipios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(municipio.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()))
                .andExpect(jsonPath("$.[0].cabeceraMunicipal").value(DEFAULT_CABECERA_MUNICIPAL.toString()));
    }

    @Test
    @Transactional
    public void getMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", municipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(municipio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cabeceraMunicipal").value(DEFAULT_CABECERA_MUNICIPAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipio() throws Exception {
        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Update the municipio
        municipio.setNombre(UPDATED_NOMBRE);
        municipio.setCabeceraMunicipal(UPDATED_CABECERA_MUNICIPAL);
        restMunicipioMockMvc.perform(post("/api/municipios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(municipio)))
                .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipios = municipioRepository.findAll();
        assertThat(municipios).hasSize(1);
        Municipio testMunicipio = municipios.iterator().next();
        assertThat(testMunicipio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMunicipio.getCabeceraMunicipal()).isEqualTo(UPDATED_CABECERA_MUNICIPAL);
    }

    @Test
    @Transactional
    public void deleteMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(delete("/api/municipios/{id}", municipio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Municipio> municipios = municipioRepository.findAll();
        assertThat(municipios).hasSize(0);
    }
}
