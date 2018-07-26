package br.mp.mpro.algamoney.service.impl;

import br.mp.mpro.algamoney.service.CategoriaService;
import br.mp.mpro.algamoney.domain.Categoria;
import br.mp.mpro.algamoney.repository.CategoriaRepository;
import br.mp.mpro.algamoney.service.dto.CategoriaDTO;
import br.mp.mpro.algamoney.service.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Categoria.
 */
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categorias");
        return categoriaRepository.findAll(pageable)
            .map(categoriaMapper::toDto);
    }


    /**
     * Get one categoria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        return categoriaRepository.findById(id)
            .map(categoriaMapper::toDto);
    }

    /**
     * Delete the categoria by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
    }
}
