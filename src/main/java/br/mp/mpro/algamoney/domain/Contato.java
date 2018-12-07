package br.mp.mpro.algamoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Contato.
 */
@Entity
@Table(name = "contato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo")
    private Long codigo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "teste")
    private String teste;

    @Column(name = "teste_2")
    private String teste2;

    @Column(name = "teste_3")
    private String teste3;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contatos")
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Contato codigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Contato nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Contato email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Contato telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTeste() {
        return teste;
    }

    public Contato teste(String teste) {
        this.teste = teste;
        return this;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public String getTeste2() {
        return teste2;
    }

    public Contato teste2(String teste2) {
        this.teste2 = teste2;
        return this;
    }

    public void setTeste2(String teste2) {
        this.teste2 = teste2;
    }

    public String getTeste3() {
        return teste3;
    }

    public Contato teste3(String teste3) {
        this.teste3 = teste3;
        return this;
    }

    public void setTeste3(String teste3) {
        this.teste3 = teste3;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Contato pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contato contato = (Contato) o;
        if (contato.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contato.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contato{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", teste='" + getTeste() + "'" +
            ", teste2='" + getTeste2() + "'" +
            ", teste3='" + getTeste3() + "'" +
            "}";
    }
}
