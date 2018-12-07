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

import br.mp.mpro.algamoney.domain.Categoria;
import br.mp.mpro.algamoney.domain.*; // for static metamodels
import br.mp.mpro.algamoney.repository.CategoriaRepository;
import br.mp.mpro.algamoney.service.dto.CategoriaCriteria;

import br.mp.mpro.algamoney.service.dto.CategoriaDTO;
import br.mp.mpro.algamoney.service.mapper.CategoriaMapper;

/**
 * Service for executing complex queries for Categoria entities in the database.
 * The main input is a {@link CategoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaDTO} or a {@link Page} of {@link CategoriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaQueryService extends QueryService<Categoria> {

    private final Logger log = LoggerFactory.getLogger(CategoriaQueryService.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public CategoriaQueryService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findByCriteria(CategoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Categoria> specification = createSpecification(criteria);
        return categoriaMapper.toDto(categoriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findByCriteria(CategoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Categoria> specification = createSpecification(criteria);
        return categoriaRepository.findAll(specification, page)
            .map(categoriaMapper::toDto);
    }

    /**
     * Function to convert CategoriaCriteria to a {@link Specification}
     */
    private Specification<Categoria> createSpecification(CategoriaCriteria criteria) {
        Specification<Categoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Categoria_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Categoria_.nome));
            }
        }
        return specification;
    }

}
