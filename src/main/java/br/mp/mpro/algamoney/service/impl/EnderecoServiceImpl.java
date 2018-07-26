package br.mp.mpro.algamoney.service.impl;

import br.mp.mpro.algamoney.service.EnderecoService;
import br.mp.mpro.algamoney.domain.Endereco;
import br.mp.mpro.algamoney.repository.EnderecoRepository;
import br.mp.mpro.algamoney.service.dto.EnderecoDTO;
import br.mp.mpro.algamoney.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Endereco.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toDto);
    }


    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id)
            .map(enderecoMapper::toDto);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
