package br.mp.mpro.algamoney.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.domain.*; // for static metamodels
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;

import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.mapper.LancamentoMapper;

/**
 * Service for executing complex queries for Lancamento entities in the database.
 * The main input is a {@link LancamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LancamentoDTO} or a {@link Page} of {@link LancamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LancamentoQueryService extends QueryService<Lancamento> {

    private final Logger log = LoggerFactory.getLogger(LancamentoQueryService.class);

    private final LancamentoRepository lancamentoRepository;

    private final LancamentoMapper lancamentoMapper;

    public LancamentoQueryService(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
    }

    /**
     * Return a {@link List} of {@link LancamentoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LancamentoDTO> findByCriteria(LancamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoMapper.toDto(lancamentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LancamentoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findByCriteria(LancamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoRepository.findAll(specification, page)
            .map(lancamentoMapper::toDto);
    }

    /**
     * Function to convert LancamentoCriteria to a {@link Specification}
     */
    private Specification<Lancamento> createSpecification(LancamentoCriteria criteria) {
        Specification<Lancamento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Lancamento_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Lancamento_.descricao));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), Lancamento_.dataVencimento));
            }
            if (criteria.getDataPagamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPagamento(), Lancamento_.dataPagamento));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Lancamento_.valor));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), Lancamento_.observacao));
            }
            if (criteria.getTipoLancamento() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoLancamento(), Lancamento_.tipoLancamento));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoriaId(), Lancamento_.categoria, Categoria_.id));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPessoaId(), Lancamento_.pessoa, Pessoa_.id));
            }
        }
        return specification;
    }

}
