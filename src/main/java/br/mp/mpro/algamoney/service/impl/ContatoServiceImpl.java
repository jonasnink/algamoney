package br.mp.mpro.algamoney.service.impl;

import br.mp.mpro.algamoney.service.ContatoService;
import br.mp.mpro.algamoney.domain.Contato;
import br.mp.mpro.algamoney.repository.ContatoRepository;
import br.mp.mpro.algamoney.service.dto.ContatoDTO;
import br.mp.mpro.algamoney.service.mapper.ContatoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Contato.
 */
@Service
@Transactional
public class ContatoServiceImpl implements ContatoService {

    private final Logger log = LoggerFactory.getLogger(ContatoServiceImpl.class);

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    public ContatoServiceImpl(ContatoRepository contatoRepository, ContatoMapper contatoMapper) {
        this.contatoRepository = contatoRepository;
        this.contatoMapper = contatoMapper;
    }

    /**
     * Save a contato.
     *
     * @param contatoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContatoDTO save(ContatoDTO contatoDTO) {
        log.debug("Request to save Contato : {}", contatoDTO);
        Contato contato = contatoMapper.toEntity(contatoDTO);
        contato = contatoRepository.save(contato);
        return contatoMapper.toDto(contato);
    }

    /**
     * Get all the contatoes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contatoes");
        return contatoRepository.findAll(pageable)
            .map(contatoMapper::toDto);
    }


    /**
     * Get one contato by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContatoDTO> findOne(Long id) {
        log.debug("Request to get Contato : {}", id);
        return contatoRepository.findById(id)
            .map(contatoMapper::toDto);
    }

    /**
     * Delete the contato by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contato : {}", id);
        contatoRepository.deleteById(id);
    }
}
