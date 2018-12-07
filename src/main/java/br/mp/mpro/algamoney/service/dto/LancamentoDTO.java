package br.mp.mpro.algamoney.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.mp.mpro.algamoney.domain.enumeration.TipoLancamento;

/**
 * A DTO for the Lancamento entity.
 */
public class LancamentoDTO implements Serializable {

    private Long id;

    private String descricao;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private BigDecimal valor;

    private String observacao;

    private TipoLancamento tipoLancamento;

    private String anexo;

    private Long categoriaId;

    private String categoriaNome;

    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
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

        LancamentoDTO lancamentoDTO = (LancamentoDTO) o;
        if (lancamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", valor=" + getValor() +
            ", observacao='" + getObservacao() + "'" +
            ", tipoLancamento='" + getTipoLancamento() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", categoria=" + getCategoriaId() +
            ", categoria='" + getCategoriaNome() + "'" +
            ", pessoa=" + getPessoaId() +
            ", pessoa='" + getPessoaNome() + "'" +
            "}";
    }
}
