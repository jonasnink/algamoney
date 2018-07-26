package br.mp.mpro.algamoney.service.impl;

import br.mp.mpro.algamoney.service.LancamentoService;
import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;
import br.mp.mpro.algamoney.service.mapper.LancamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Lancamento.
 */
@Service
@Transactional
public class LancamentoServiceImpl implements LancamentoService {

    private final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    private final LancamentoRepository lancamentoRepository;

    private final LancamentoMapper lancamentoMapper;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
    }

    /**
     * Save a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
        log.debug("Request to save Lancamento : {}", lancamentoDTO);
        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        lancamento = lancamentoRepository.save(lancamento);
        return lancamentoMapper.toDto(lancamento);
    }

    /**
     * Get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lancamentos");
        return lancamentoRepository.findAll(pageable)
            .map(lancamentoMapper::toDto);
    }


    /**
     * Get one lancamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LancamentoDTO> findOne(Long id) {
        log.debug("Request to get Lancamento : {}", id);
        return lancamentoRepository.findById(id)
            .map(lancamentoMapper::toDto);
    }

    /**
     * Delete the lancamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lancamento : {}", id);
        lancamentoRepository.deleteById(id);
    }
}
