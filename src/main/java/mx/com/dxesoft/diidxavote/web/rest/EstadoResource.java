package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Estado;
import mx.com.dxesoft.diidxavote.repository.EstadoRepository;
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
 * REST controller for managing Estado.
 */
@RestController
@RequestMapping("/api")
public class EstadoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoResource.class);

    @Inject
    private EstadoRepository estadoRepository;

    /**
     * POST  /estados -> Create a new estado.
     */
    @RequestMapping(value = "/estados",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Estado estado) {
        log.debug("REST request to save Estado : {}", estado);
        estadoRepository.save(estado);
    }

    /**
     * GET  /estados -> get all the estados.
     */
    @RequestMapping(value = "/estados",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Estado> getAll() {
        log.debug("REST request to get all Estados");
        return estadoRepository.findAll();
    }

    /**
     * GET  /estados/:id -> get the "id" estado.
     */
    @RequestMapping(value = "/estados/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estado> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Estado : {}", id);
        Estado estado = estadoRepository.findOne(id);
        if (estado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }

    /**
     * DELETE  /estados/:id -> delete the "id" estado.
     */
    @RequestMapping(value = "/estados/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Estado : {}", id);
        estadoRepository.delete(id);
    }
}
