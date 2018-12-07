package br.mp.mpro.algamoney.service.dto.custom;

import br.mp.mpro.algamoney.domain.Pessoa;
import br.mp.mpro.algamoney.domain.enumeration.TipoLancamento;
import br.mp.mpro.algamoney.service.custom.LancamentoQueryServiceCustom;

import java.math.BigDecimal;

public class LancamentoEstatisticaPessoa {
    private TipoLancamento tipo;

    private Pessoa pessoa;

    private BigDecimal total;

    private LancamentoQueryServiceCustom lancamentoQueryServiceCustom;

    public LancamentoEstatisticaPessoa(){

    }

    public LancamentoEstatisticaPessoa(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.total = total;
    }


    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
