package br.mp.mpro.algamoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 4)
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ativo")
    private Boolean ativo;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "img_arquivo")
    private byte[] imgArquivo;

    @Column(name = "img_arquivo_content_type")
    private String imgArquivoContentType;

    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "arquivo_content_type")
    private String arquivoContentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Endereco endereco;

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contato> contatos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Pessoa ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Pessoa foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Pessoa fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImgArquivo() {
        return imgArquivo;
    }

    public Pessoa imgArquivo(byte[] imgArquivo) {
        this.imgArquivo = imgArquivo;
        return this;
    }

    public void setImgArquivo(byte[] imgArquivo) {
        this.imgArquivo = imgArquivo;
    }

    public String getImgArquivoContentType() {
        return imgArquivoContentType;
    }

    public Pessoa imgArquivoContentType(String imgArquivoContentType) {
        this.imgArquivoContentType = imgArquivoContentType;
        return this;
    }

    public void setImgArquivoContentType(String imgArquivoContentType) {
        this.imgArquivoContentType = imgArquivoContentType;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public Pessoa arquivo(byte[] arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getArquivoContentType() {
        return arquivoContentType;
    }

    public Pessoa arquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
        return this;
    }

    public void setArquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Pessoa endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Contato> getContatos() {
        return contatos;
    }

    public Pessoa contatos(Set<Contato> contatoes) {
        this.contatos = contatoes;
        return this;
    }

    public Pessoa addContatos(Contato contato) {
        this.contatos.add(contato);
        contato.setPessoa(this);
        return this;
    }

    public Pessoa removeContatos(Contato contato) {
        this.contatos.remove(contato);
        contato.setPessoa(null);
        return this;
    }

    public void setContatos(Set<Contato> contatoes) {
        this.contatos = contatoes;
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
        Pessoa pessoa = (Pessoa) o;
        if (pessoa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", imgArquivo='" + getImgArquivo() + "'" +
            ", imgArquivoContentType='" + getImgArquivoContentType() + "'" +
            ", arquivo='" + getArquivo() + "'" +
            ", arquivoContentType='" + getArquivoContentType() + "'" +
            "}";
    }
}
