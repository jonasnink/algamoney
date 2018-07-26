package br.mp.mpro.algamoney.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pessoa entity.
 */
public class PessoaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4)
    private String nome;

    private Boolean ativo;

    private Long enderecoId;

    private String enderecoNumero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PessoaDTO pessoaDTO = (PessoaDTO) o;
        if (pessoaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PessoaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", endereco=" + getEnderecoId() +
            ", endereco='" + getEnderecoNumero() + "'" +
            "}";
    }
}
