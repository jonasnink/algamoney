package br.mp.mpro.algamoney.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.algamoney.service.EnderecoService;
import br.mp.mpro.algamoney.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.algamoney.web.rest.util.HeaderUtil;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import br.mp.mpro.algamoney.service.dto.EnderecoDTO;
import br.mp.mpro.algamoney.service.dto.EnderecoCriteria;
import br.mp.mpro.algamoney.service.EnderecoQueryService;
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
 * REST controller for managing Endereco.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    private final EnderecoService enderecoService;

    private final EnderecoQueryService enderecoQueryService;

    public EnderecoResource(EnderecoService enderecoService, EnderecoQueryService enderecoQueryService) {
        this.enderecoService = enderecoService;
        this.enderecoQueryService = enderecoQueryService;
    }

    /**
     * POST  /enderecos : Create a new endereco.
     *
     * @param enderecoDTO the enderecoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enderecoDTO, or with status 400 (Bad Request) if the endereco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enderecos")
    @Timed
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enderecos : Updates an existing endereco.
     *
     * @param enderecoDTO the enderecoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enderecoDTO,
     * or with status 400 (Bad Request) if the enderecoDTO is not valid,
     * or with status 500 (Internal Server Error) if the enderecoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enderecos")
    @Timed
    public ResponseEntity<EnderecoDTO> updateEndereco(@RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enderecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enderecos : get all the enderecos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of enderecos in body
     */
    @GetMapping("/enderecos")
    @Timed
    public ResponseEntity<List<EnderecoDTO>> getAllEnderecos(EnderecoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enderecos by criteria: {}", criteria);
        Page<EnderecoDTO> page = enderecoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enderecos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enderecos/:id : get the "id" endereco.
     *
     * @param id the id of the enderecoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enderecoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enderecos/{id}")
    @Timed
    public ResponseEntity<EnderecoDTO> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<EnderecoDTO> enderecoDTO = enderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoDTO);
    }

    /**
     * DELETE  /enderecos/:id : delete the "id" endereco.
     *
     * @param id the id of the enderecoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enderecos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
