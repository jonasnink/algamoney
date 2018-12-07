package br.mp.mpro.algamoney.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.algamoney.service.LancamentoService;
import br.mp.mpro.algamoney.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.algamoney.web.rest.util.HeaderUtil;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;
import br.mp.mpro.algamoney.service.LancamentoQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lancamento.
 */
@RestController
@RequestMapping("/api")
public class LancamentoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;

    private final LancamentoQueryService lancamentoQueryService;

    public LancamentoResource(LancamentoService lancamentoService, LancamentoQueryService lancamentoQueryService) {
        this.lancamentoService = lancamentoService;
        this.lancamentoQueryService = lancamentoQueryService;
    }

    /**
     * POST  /lancamentos : Create a new lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamentoDTO, or with status 400 (Bad Request) if the lancamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> createLancamento(@RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamentos : Updates an existing lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamentoDTO,
     * or with status 400 (Bad Request) if the lancamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the lancamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> updateLancamento(@RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamentos : get all the lancamentos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentos in body
     */
    @GetMapping("/lancamentos")
    @Timed
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentos(LancamentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Lancamentos by criteria: {}", criteria);
        Page<LancamentoDTO> page = lancamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lancamentos/:id : get the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lancamentos/{id}")
    @Timed
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        log.debug("REST request to get Lancamento : {}", id);
        Optional<LancamentoDTO> lancamentoDTO = lancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoDTO);
    }

    /**
     * DELETE  /lancamentos/:id : delete the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        log.debug("REST request to delete Lancamento : {}", id);
        lancamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
