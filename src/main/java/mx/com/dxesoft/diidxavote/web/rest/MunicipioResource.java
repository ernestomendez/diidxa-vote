package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Municipio;
import mx.com.dxesoft.diidxavote.repository.MunicipioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Municipio.
 */
@RestController
@RequestMapping("/api")
public class MunicipioResource {

    private final Logger log = LoggerFactory.getLogger(MunicipioResource.class);

    @Inject
    private MunicipioRepository municipioRepository;

    /**
     * POST  /municipios -> Create a new municipio.
     */
    @RequestMapping(value = "/municipios",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Municipio municipio) {
        log.debug("REST request to save Municipio : {}", municipio);
        municipioRepository.save(municipio);
    }

    /**
     * GET  /municipios -> get all the municipios.
     */
    @RequestMapping(value = "/municipios",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Municipio> getAll() {
        log.debug("REST request to get all Municipios");
        return municipioRepository.findAll();
    }

    /**
     * GET  /municipios/:id -> get the "id" municipio.
     */
    @RequestMapping(value = "/municipios/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Municipio> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Municipio : {}", id);
        Municipio municipio = municipioRepository.findOne(id);
        if (municipio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(municipio, HttpStatus.OK);
    }

    /**
     * DELETE  /municipios/:id -> delete the "id" municipio.
     */
    @RequestMapping(value = "/municipios/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Municipio : {}", id);
        municipioRepository.delete(id);
    }
}
