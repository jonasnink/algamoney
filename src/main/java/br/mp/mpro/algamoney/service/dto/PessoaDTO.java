package br.mp.mpro.algamoney.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Pessoa entity.
 */
public class PessoaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4)
    private String nome;

    private Boolean ativo;

    @Lob
    private byte[] foto;
    private String fotoContentType;

    private String email;

    @Lob
    private byte[] imgArquivo;
    private String imgArquivoContentType;

    @Lob
    private byte[] arquivo;
    private String arquivoContentType;

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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImgArquivo() {
        return imgArquivo;
    }

    public void setImgArquivo(byte[] imgArquivo) {
        this.imgArquivo = imgArquivo;
    }

    public String getImgArquivoContentType() {
        return imgArquivoContentType;
    }

    public void setImgArquivoContentType(String imgArquivoContentType) {
        this.imgArquivoContentType = imgArquivoContentType;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getArquivoContentType() {
        return arquivoContentType;
    }

    public void setArquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
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
            ", foto='" + getFoto() + "'" +
            ", email='" + getEmail() + "'" +
            ", imgArquivo='" + getImgArquivo() + "'" +
            ", arquivo='" + getArquivo() + "'" +
            ", endereco=" + getEnderecoId() +
            ", endereco='" + getEnderecoNumero() + "'" +
            "}";
    }
}
