package br.mp.mpro.algamoney.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Contato entity.
 */
public class ContatoDTO implements Serializable {

    private Long id;

    private Long codigo;

    @NotNull
    private String nome;

    @NotNull
    private String email;

    @NotNull
    private String telefone;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String teste;

    private String teste2;

    private String teste3;

    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public String getTeste2() {
        return teste2;
    }

    public void setTeste2(String teste2) {
        this.teste2 = teste2;
    }

    public String getTeste3() {
        return teste3;
    }

    public void setTeste3(String teste3) {
        this.teste3 = teste3;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContatoDTO contatoDTO = (ContatoDTO) o;
        if (contatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContatoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", teste='" + getTeste() + "'" +
            ", teste2='" + getTeste2() + "'" +
            ", teste3='" + getTeste3() + "'" +
            ", pessoa=" + getPessoaId() +
            ", pessoa='" + getPessoaNome() + "'" +
            "}";
    }
}
