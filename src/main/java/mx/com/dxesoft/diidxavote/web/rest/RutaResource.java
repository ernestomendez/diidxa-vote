package mx.com.dxesoft.diidxavote.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.dxesoft.diidxavote.domain.Ruta;
import mx.com.dxesoft.diidxavote.repository.RutaRepository;
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
 * REST controller for managing Ruta.
 */
@RestController
@RequestMapping("/api")
public class RutaResource {

    private final Logger log = LoggerFactory.getLogger(RutaResource.class);

    @Inject
    private RutaRepository rutaRepository;

    /**
     * POST  /rutas -> Create a new ruta.
     */
    @RequestMapping(value = "/rutas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Ruta ruta) {
        log.debug("REST request to save Ruta : {}", ruta);
        rutaRepository.save(ruta);
    }

    /**
     * GET  /rutas -> get all the rutas.
     */
    @RequestMapping(value = "/rutas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ruta> getAll() {
        log.debug("REST request to get all Rutas");
        return rutaRepository.findAll();
    }

    /**
     * GET  /rutas/:id -> get the "id" ruta.
     */
    @RequestMapping(value = "/rutas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ruta> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Ruta : {}", id);
        Ruta ruta = rutaRepository.findOne(id);
        if (ruta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ruta, HttpStatus.OK);
    }

    /**
     * DELETE  /rutas/:id -> delete the "id" ruta.
     */
    @RequestMapping(value = "/rutas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Ruta : {}", id);
        rutaRepository.delete(id);
    }
}
