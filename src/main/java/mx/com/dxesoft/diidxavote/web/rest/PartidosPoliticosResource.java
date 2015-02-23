package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.PartidosPoliticos;
import mx.com.dxesoft.diidxavote.repository.PartidosPoliticosRepository;
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
 * REST controller for managing PartidosPoliticos.
 */
@RestController
@RequestMapping("/api")
public class PartidosPoliticosResource {

    private final Logger log = LoggerFactory.getLogger(PartidosPoliticosResource.class);

    @Inject
    private PartidosPoliticosRepository partidosPoliticosRepository;

    /**
     * POST  /partidosPoliticoss -> Create a new partidosPoliticos.
     */
    @RequestMapping(value = "/partidosPoliticoss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody PartidosPoliticos partidosPoliticos) {
        log.debug("REST request to save PartidosPoliticos : {}", partidosPoliticos);
        partidosPoliticosRepository.save(partidosPoliticos);
    }

    /**
     * GET  /partidosPoliticoss -> get all the partidosPoliticoss.
     */
    @RequestMapping(value = "/partidosPoliticoss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PartidosPoliticos> getAll() {
        log.debug("REST request to get all PartidosPoliticoss");
        return partidosPoliticosRepository.findAll();
    }

    /**
     * GET  /partidosPoliticoss/:id -> get the "id" partidosPoliticos.
     */
    @RequestMapping(value = "/partidosPoliticoss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartidosPoliticos> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PartidosPoliticos : {}", id);
        PartidosPoliticos partidosPoliticos = partidosPoliticosRepository.findOne(id);
        if (partidosPoliticos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(partidosPoliticos, HttpStatus.OK);
    }

    /**
     * DELETE  /partidosPoliticoss/:id -> delete the "id" partidosPoliticos.
     */
    @RequestMapping(value = "/partidosPoliticoss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PartidosPoliticos : {}", id);
        partidosPoliticosRepository.delete(id);
    }
}
