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
 * Criteria class for the Contato entity. This class is used in ContatoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contatoes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContatoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter codigo;

    private StringFilter nome;

    private StringFilter email;

    private StringFilter telefone;

    private StringFilter teste;

    private StringFilter teste2;

    private StringFilter teste3;

    private LongFilter pessoaId;

    public ContatoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCodigo() {
        return codigo;
    }

    public void setCodigo(LongFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getTeste() {
        return teste;
    }

    public void setTeste(StringFilter teste) {
        this.teste = teste;
    }

    public StringFilter getTeste2() {
        return teste2;
    }

    public void setTeste2(StringFilter teste2) {
        this.teste2 = teste2;
    }

    public StringFilter getTeste3() {
        return teste3;
    }

    public void setTeste3(StringFilter teste3) {
        this.teste3 = teste3;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public String toString() {
        return "ContatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codigo != null ? "codigo=" + codigo + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (telefone != null ? "telefone=" + telefone + ", " : "") +
                (teste != null ? "teste=" + teste + ", " : "") +
                (teste2 != null ? "teste2=" + teste2 + ", " : "") +
                (teste3 != null ? "teste3=" + teste3 + ", " : "") +
                (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
            "}";
    }

}
