package br.mp.mpro.algamoney.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.algamoney.service.CategoriaService;
import br.mp.mpro.algamoney.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.algamoney.web.rest.util.HeaderUtil;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import br.mp.mpro.algamoney.service.dto.CategoriaDTO;
import br.mp.mpro.algamoney.service.dto.CategoriaCriteria;
import br.mp.mpro.algamoney.service.CategoriaQueryService;
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
 * REST controller for managing Categoria.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    private static final String ENTITY_NAME = "categoria";

    private final CategoriaService categoriaService;

    private final CategoriaQueryService categoriaQueryService;

    public CategoriaResource(CategoriaService categoriaService, CategoriaQueryService categoriaQueryService) {
        this.categoriaService = categoriaService;
        this.categoriaQueryService = categoriaQueryService;
    }

    /**
     * POST  /categorias : Create a new categoria.
     *
     * @param categoriaDTO the categoriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaDTO, or with status 400 (Bad Request) if the categoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorias")
    @Timed
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorias : Updates an existing categoria.
     *
     * @param categoriaDTO the categoriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaDTO,
     * or with status 400 (Bad Request) if the categoriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoriaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorias")
    @Timed
    public ResponseEntity<CategoriaDTO> updateCategoria(@RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorias : get all the categorias.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of categorias in body
     */
    @GetMapping("/categorias")
    @Timed
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(CategoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Categorias by criteria: {}", criteria);
        Page<CategoriaDTO> page = categoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categorias/:id : get the "id" categoria.
     *
     * @param id the id of the categoriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorias/{id}")
    @Timed
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<CategoriaDTO> categoriaDTO = categoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaDTO);
    }

    /**
     * DELETE  /categorias/:id : delete the "id" categoria.
     *
     * @param id the id of the categoriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorias/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
