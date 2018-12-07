package br.mp.mpro.algamoney.service.dto;

import java.io.Serializable;
import br.mp.mpro.algamoney.domain.enumeration.TipoLancamento;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Lancamento entity. This class is used in LancamentoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /lancamentos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LancamentoCriteria implements Serializable {
    /**
     * Class for filtering TipoLancamento
     */
    public static class TipoLancamentoFilter extends Filter<TipoLancamento> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter descricao;

    private LocalDateFilter dataVencimento;

    private LocalDateFilter dataPagamento;

    private BigDecimalFilter valor;

    private StringFilter observacao;

    private TipoLancamentoFilter tipoLancamento;

    private StringFilter anexo;

    private LongFilter categoriaId;

    private LongFilter pessoaId;

    public LancamentoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LocalDateFilter getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateFilter getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateFilter dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
    }

    public TipoLancamentoFilter getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamentoFilter tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public StringFilter getAnexo() {
        return anexo;
    }

    public void setAnexo(StringFilter anexo) {
        this.anexo = anexo;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public String toString() {
        return "LancamentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (dataVencimento != null ? "dataVencimento=" + dataVencimento + ", " : "") +
                (dataPagamento != null ? "dataPagamento=" + dataPagamento + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (tipoLancamento != null ? "tipoLancamento=" + tipoLancamento + ", " : "") +
                (anexo != null ? "anexo=" + anexo + ", " : "") +
                (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
                (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
            "}";
    }

}
