package br.mp.mpro.algamoney.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Pessoa entity. This class is used in PessoaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pessoas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PessoaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter ativo;

    private StringFilter email;

    private LongFilter enderecoId;

    private LongFilter contatosId;

    public PessoaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }

    public LongFilter getContatosId() {
        return contatosId;
    }

    public void setContatosId(LongFilter contatosId) {
        this.contatosId = contatosId;
    }

    @Override
    public String toString() {
        return "PessoaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
                (contatosId != null ? "contatosId=" + contatosId + ", " : "") +
            "}";
    }

}
