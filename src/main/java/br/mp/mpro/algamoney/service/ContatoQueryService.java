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

import br.mp.mpro.algamoney.domain.Contato;
import br.mp.mpro.algamoney.domain.*; // for static metamodels
import br.mp.mpro.algamoney.repository.ContatoRepository;
import br.mp.mpro.algamoney.service.dto.ContatoCriteria;

import br.mp.mpro.algamoney.service.dto.ContatoDTO;
import br.mp.mpro.algamoney.service.mapper.ContatoMapper;

/**
 * Service for executing complex queries for Contato entities in the database.
 * The main input is a {@link ContatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoDTO} or a {@link Page} of {@link ContatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoQueryService extends QueryService<Contato> {

    private final Logger log = LoggerFactory.getLogger(ContatoQueryService.class);

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    public ContatoQueryService(ContatoRepository contatoRepository, ContatoMapper contatoMapper) {
        this.contatoRepository = contatoRepository;
        this.contatoMapper = contatoMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoDTO> findByCriteria(ContatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoMapper.toDto(contatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoDTO> findByCriteria(ContatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoRepository.findAll(specification, page)
            .map(contatoMapper::toDto);
    }

    /**
     * Function to convert ContatoCriteria to a {@link Specification}
     */
    private Specification<Contato> createSpecification(ContatoCriteria criteria) {
        Specification<Contato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Contato_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodigo(), Contato_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Contato_.nome));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contato_.email));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Contato_.telefone));
            }
            if (criteria.getTeste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeste(), Contato_.teste));
            }
            if (criteria.getTeste2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeste2(), Contato_.teste2));
            }
            if (criteria.getTeste3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeste3(), Contato_.teste3));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPessoaId(), Contato_.pessoa, Pessoa_.id));
            }
        }
        return specification;
    }

}
