package br.mp.mpro.algamoney.service.mapper;

import br.mp.mpro.algamoney.domain.*;
import br.mp.mpro.algamoney.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Endereco and its DTO EnderecoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {



    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
