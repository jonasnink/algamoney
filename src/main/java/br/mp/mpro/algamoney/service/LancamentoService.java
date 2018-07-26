package br.mp.mpro.algamoney.service;

import br.mp.mpro.algamoney.service.dto.LancamentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Lancamento.
 */
public interface LancamentoService {

    /**
     * Save a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    LancamentoDTO save(LancamentoDTO lancamentoDTO);

    /**
     * Get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LancamentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lancamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LancamentoDTO> findOne(Long id);

    /**
     * Delete the "id" lancamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
