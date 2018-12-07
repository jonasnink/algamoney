package br.mp.mpro.algamoney.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.algamoney.service.ContatoService;
import br.mp.mpro.algamoney.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.algamoney.web.rest.util.HeaderUtil;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import br.mp.mpro.algamoney.service.dto.ContatoDTO;
import br.mp.mpro.algamoney.service.dto.ContatoCriteria;
import br.mp.mpro.algamoney.service.ContatoQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contato.
 */
@RestController
@RequestMapping("/api")
public class ContatoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoResource.class);

    private static final String ENTITY_NAME = "contato";

    private final ContatoService contatoService;

    private final ContatoQueryService contatoQueryService;

    public ContatoResource(ContatoService contatoService, ContatoQueryService contatoQueryService) {
        this.contatoService = contatoService;
        this.contatoQueryService = contatoQueryService;
    }

    /**
     * POST  /contatoes : Create a new contato.
     *
     * @param contatoDTO the contatoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contatoDTO, or with status 400 (Bad Request) if the contato has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contatoes")
    @Timed
    public ResponseEntity<ContatoDTO> createContato(@Valid @RequestBody ContatoDTO contatoDTO) throws URISyntaxException {
        log.debug("REST request to save Contato : {}", contatoDTO);
        if (contatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContatoDTO result = contatoService.save(contatoDTO);
        return ResponseEntity.created(new URI("/api/contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contatoes : Updates an existing contato.
     *
     * @param contatoDTO the contatoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contatoDTO,
     * or with status 400 (Bad Request) if the contatoDTO is not valid,
     * or with status 500 (Internal Server Error) if the contatoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contatoes")
    @Timed
    public ResponseEntity<ContatoDTO> updateContato(@Valid @RequestBody ContatoDTO contatoDTO) throws URISyntaxException {
        log.debug("REST request to update Contato : {}", contatoDTO);
        if (contatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContatoDTO result = contatoService.save(contatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contatoes : get all the contatoes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contatoes in body
     */
    @GetMapping("/contatoes")
    @Timed
    public ResponseEntity<List<ContatoDTO>> getAllContatoes(ContatoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contatoes by criteria: {}", criteria);
        Page<ContatoDTO> page = contatoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contatoes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contatoes/:id : get the "id" contato.
     *
     * @param id the id of the contatoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contatoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contatoes/{id}")
    @Timed
    public ResponseEntity<ContatoDTO> getContato(@PathVariable Long id) {
        log.debug("REST request to get Contato : {}", id);
        Optional<ContatoDTO> contatoDTO = contatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contatoDTO);
    }

    /**
     * DELETE  /contatoes/:id : delete the "id" contato.
     *
     * @param id the id of the contatoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contatoes/{id}")
    @Timed
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.debug("REST request to delete Contato : {}", id);
        contatoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
