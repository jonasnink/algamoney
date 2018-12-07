package br.mp.mpro.algamoney.service.custom;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.service.LancamentoQueryService;
import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.dto.custom.LancamentoCriteriaCustom;
import br.mp.mpro.algamoney.service.mapper.LancamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.mp.mpro.algamoney.domain.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Primary
public class LancamentoQueryServiceCustom extends LancamentoQueryService {
    private final Logger log = LoggerFactory.getLogger(LancamentoQueryService.class);
    private final LancamentoRepository lancamentoRepository;
    private final LancamentoMapper lancamentoMapper;

    public LancamentoQueryServiceCustom(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper) {
        super(lancamentoRepository, lancamentoMapper);
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
    }

    @Transactional(readOnly = true)
    public List<LancamentoDTO> findByCriteria(LancamentoCriteriaCustom criteria) {
        log.debug("find by criteria : {}", criteria);
        // teste de comentario
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoMapper.toDto(lancamentoRepository.findAll(specification));
    }

    public List<Lancamento> findByEntityCriteria(LancamentoCriteriaCustom criteria){
        log.debug("find by criteria: {}", criteria);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoRepository.findAll(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findByCriteria(LancamentoCriteria criteria, Pageable page) {
        return super.findByCriteria(criteria, page);
    }

    private Specification<Lancamento> createSpecification(LancamentoCriteriaCustom criteria) {
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
            if(criteria.getDataVencimentoInicio() != null){
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimentoInicio(), Lancamento_.dataVencimento));
            }
            if(criteria.getDataVencimentoFim() != null){
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimentoFim(), Lancamento_.dataVencimento));
            }
        }
        return specification;
    }
}
