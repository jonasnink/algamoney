package br.mp.mpro.algamoney.web.rest;


import br.mp.mpro.algamoney.service.LancamentoQueryService;
import br.mp.mpro.algamoney.service.LancamentoService;
import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/custom")
public class LancamentoResourceCustom extends LancamentoResource {
    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;
    private final LancamentoQueryService lancamentoQueryService;
    public LancamentoResourceCustom(LancamentoService lancamentoService, LancamentoQueryService lancamentoQueryService) {
        super(lancamentoService, lancamentoQueryService);
        this.lancamentoService = lancamentoService;
        this.lancamentoQueryService = lancamentoQueryService;
    }

//    @GetMapping("/lancamentos")
//    @Timed
//    public ResponseEntity<List<LancamentoDTO>> getAllLancamentos(LancamentoCriteria criteria, Pageable pageable) {
//        log.debug("REST request to get Lancamentos by criteria: {}", criteria);
//        Page<LancamentoDTO> page = lancamentoQueryService.findByCriteria(criteria, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }


    @GetMapping("/lancamentos/resumo")
    @Timed
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentosResumo(Pageable pageable) {
        log.debug("REST request to get a page of Lancamentos");
        Page<LancamentoDTO> page = lancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos/resumo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
