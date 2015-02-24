package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Elector;
import mx.com.dxesoft.diidxavote.repository.ElectorRepository;
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
 * REST controller for managing Elector.
 */
@RestController
@RequestMapping("/api")
public class ElectorResource {

    private final Logger log = LoggerFactory.getLogger(ElectorResource.class);

    @Inject
    private ElectorRepository electorRepository;

    /**
     * POST  /electors -> Create a new elector.
     */
    @RequestMapping(value = "/electors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Elector elector) {
        log.debug("REST request to save Elector : {}", elector);
        electorRepository.save(elector);
    }

    /**
     * GET  /electors -> get all the electors.
     */
    @RequestMapping(value = "/electors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Elector> getAll() {
        log.debug("REST request to get all Electors");
        return electorRepository.findAll();
    }

    /**
     * GET  /electors/:id -> get the "id" elector.
     */
    @RequestMapping(value = "/electors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Elector> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Elector : {}", id);
        Elector elector = electorRepository.findOne(id);
        if (elector == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(elector, HttpStatus.OK);
    }

    /**
     * DELETE  /electors/:id -> delete the "id" elector.
     */
    @RequestMapping(value = "/electors/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Elector : {}", id);
        electorRepository.delete(id);
    }
}
