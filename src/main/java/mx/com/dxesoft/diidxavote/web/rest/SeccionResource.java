package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Seccion;
import mx.com.dxesoft.diidxavote.repository.SeccionRepository;
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
 * REST controller for managing Seccion.
 */
@RestController
@RequestMapping("/api")
public class SeccionResource {

    private final Logger log = LoggerFactory.getLogger(SeccionResource.class);

    @Inject
    private SeccionRepository seccionRepository;

    /**
     * POST  /seccions -> Create a new seccion.
     */
    @RequestMapping(value = "/seccions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Seccion seccion) {
        log.debug("REST request to save Seccion : {}", seccion);
        seccionRepository.save(seccion);
    }

    /**
     * GET  /seccions -> get all the seccions.
     */
    @RequestMapping(value = "/seccions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Seccion> getAll() {
        log.debug("REST request to get all Seccions");
        return seccionRepository.findAll();
    }

    /**
     * GET  /seccions/:id -> get the "id" seccion.
     */
    @RequestMapping(value = "/seccions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Seccion> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Seccion : {}", id);
        Seccion seccion = seccionRepository.findOne(id);
        if (seccion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seccion, HttpStatus.OK);
    }

    /**
     * DELETE  /seccions/:id -> delete the "id" seccion.
     */
    @RequestMapping(value = "/seccions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Seccion : {}", id);
        seccionRepository.delete(id);
    }
}
