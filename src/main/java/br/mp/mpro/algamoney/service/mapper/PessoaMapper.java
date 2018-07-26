package br.mp.mpro.algamoney.service.mapper;

import br.mp.mpro.algamoney.domain.*;
import br.mp.mpro.algamoney.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pessoa and its DTO PessoaDTO.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "endereco.numero", target = "enderecoNumero")
    PessoaDTO toDto(Pessoa pessoa);

    @Mapping(source = "enderecoId", target = "endereco")
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
