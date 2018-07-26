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

import br.mp.mpro.algamoney.domain.Endereco;
import br.mp.mpro.algamoney.domain.*; // for static metamodels
import br.mp.mpro.algamoney.repository.EnderecoRepository;
import br.mp.mpro.algamoney.service.dto.EnderecoCriteria;

import br.mp.mpro.algamoney.service.dto.EnderecoDTO;
import br.mp.mpro.algamoney.service.mapper.EnderecoMapper;

/**
 * Service for executing complex queries for Endereco entities in the database.
 * The main input is a {@link EnderecoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnderecoDTO} or a {@link Page} of {@link EnderecoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnderecoQueryService extends QueryService<Endereco> {

    private final Logger log = LoggerFactory.getLogger(EnderecoQueryService.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoQueryService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Return a {@link List} of {@link EnderecoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findByCriteria(EnderecoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Endereco> specification = createSpecification(criteria);
        return enderecoMapper.toDto(enderecoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnderecoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findByCriteria(EnderecoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Endereco> specification = createSpecification(criteria);
        return enderecoRepository.findAll(specification, page)
            .map(enderecoMapper::toDto);
    }

    /**
     * Function to convert EnderecoCriteria to a {@link Specification}
     */
    private Specification<Endereco> createSpecification(EnderecoCriteria criteria) {
        Specification<Endereco> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Endereco_.id));
            }
            if (criteria.getLogradouro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogradouro(), Endereco_.logradouro));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Endereco_.numero));
            }
            if (criteria.getComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplemento(), Endereco_.complemento));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), Endereco_.bairro));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Endereco_.cep));
            }
            if (criteria.getCidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidade(), Endereco_.cidade));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Endereco_.estado));
            }
        }
        return specification;
    }

}
