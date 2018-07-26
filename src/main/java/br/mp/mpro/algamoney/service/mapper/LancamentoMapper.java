package br.mp.mpro.algamoney.service.mapper;

import br.mp.mpro.algamoney.domain.*;
import br.mp.mpro.algamoney.service.dto.LancamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lancamento and its DTO LancamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, PessoaMapper.class})
public interface LancamentoMapper extends EntityMapper<LancamentoDTO, Lancamento> {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    LancamentoDTO toDto(Lancamento lancamento);

    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "pessoaId", target = "pessoa")
    Lancamento toEntity(LancamentoDTO lancamentoDTO);

    default Lancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        return lancamento;
    }
}
