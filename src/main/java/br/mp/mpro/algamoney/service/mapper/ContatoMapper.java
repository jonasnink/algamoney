package br.mp.mpro.algamoney.service.mapper;

import br.mp.mpro.algamoney.domain.*;
import br.mp.mpro.algamoney.service.dto.ContatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contato and its DTO ContatoDTO.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface ContatoMapper extends EntityMapper<ContatoDTO, Contato> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    ContatoDTO toDto(Contato contato);

    @Mapping(source = "pessoaId", target = "pessoa")
    Contato toEntity(ContatoDTO contatoDTO);

    default Contato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contato contato = new Contato();
        contato.setId(id);
        return contato;
    }
}
