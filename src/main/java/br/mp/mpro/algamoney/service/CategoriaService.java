package br.mp.mpro.algamoney.service;

import br.mp.mpro.algamoney.service.dto.CategoriaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Categoria.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoriaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
