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

import br.mp.mpro.algamoney.domain.Pessoa;
import br.mp.mpro.algamoney.domain.*; // for static metamodels
import br.mp.mpro.algamoney.repository.PessoaRepository;
import br.mp.mpro.algamoney.service.dto.PessoaCriteria;

import br.mp.mpro.algamoney.service.dto.PessoaDTO;
import br.mp.mpro.algamoney.service.mapper.PessoaMapper;

/**
 * Service for executing complex queries for Pessoa entities in the database.
 * The main input is a {@link PessoaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PessoaDTO} or a {@link Page} of {@link PessoaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PessoaQueryService extends QueryService<Pessoa> {

    private final Logger log = LoggerFactory.getLogger(PessoaQueryService.class);

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    public PessoaQueryService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    /**
     * Return a {@link List} of {@link PessoaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PessoaDTO> findByCriteria(PessoaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaMapper.toDto(pessoaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PessoaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaDTO> findByCriteria(PessoaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaRepository.findAll(specification, page)
            .map(pessoaMapper::toDto);
    }

    /**
     * Function to convert PessoaCriteria to a {@link Specification}
     */
    private Specification<Pessoa> createSpecification(PessoaCriteria criteria) {
        Specification<Pessoa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pessoa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Pessoa_.nome));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Pessoa_.ativo));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Pessoa_.email));
            }
            if (criteria.getEnderecoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEnderecoId(), Pessoa_.endereco, Endereco_.id));
            }
            if (criteria.getContatosId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getContatosId(), Pessoa_.contatos, Contato_.id));
            }
        }
        return specification;
    }

}
