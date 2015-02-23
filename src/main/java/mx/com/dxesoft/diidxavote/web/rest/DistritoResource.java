package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Distrito;
import mx.com.dxesoft.diidxavote.repository.DistritoRepository;
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
 * REST controller for managing Distrito.
 */
@RestController
@RequestMapping("/api")
public class DistritoResource {

    private final Logger log = LoggerFactory.getLogger(DistritoResource.class);

    @Inject
    private DistritoRepository distritoRepository;

    /**
     * POST  /distritos -> Create a new distrito.
     */
    @RequestMapping(value = "/distritos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Distrito distrito) {
        log.debug("REST request to save Distrito : {}", distrito);
        distritoRepository.save(distrito);
    }

    /**
     * GET  /distritos -> get all the distritos.
     */
    @RequestMapping(value = "/distritos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Distrito> getAll() {
        log.debug("REST request to get all Distritos");
        return distritoRepository.findAll();
    }

    /**
     * GET  /distritos/:id -> get the "id" distrito.
     */
    @RequestMapping(value = "/distritos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Distrito> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Distrito : {}", id);
        Distrito distrito = distritoRepository.findOne(id);
        if (distrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(distrito, HttpStatus.OK);
    }

    /**
     * DELETE  /distritos/:id -> delete the "id" distrito.
     */
    @RequestMapping(value = "/distritos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Distrito : {}", id);
        distritoRepository.delete(id);
    }
}
