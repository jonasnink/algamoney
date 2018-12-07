package br.mp.mpro.algamoney.service.dto.custom;

import br.mp.mpro.algamoney.service.dto.LancamentoCriteria;
import io.github.jhipster.service.filter.LocalDateFilter;

import java.io.Serializable;

public class LancamentoCriteriaCustom extends LancamentoCriteria{

    private LocalDateFilter dataVencimentoInicio;

    private LocalDateFilter dataVencimentoFim;

    public LocalDateFilter getDataVencimentoInicio() {
        return dataVencimentoInicio;
    }

    public void setDataVencimentoInicio(LocalDateFilter dataVencimentoInicio) {
        this.dataVencimentoInicio = dataVencimentoInicio;
    }

    public LocalDateFilter getDataVencimentoFim() {
        return dataVencimentoFim;
    }

    public void setDataVencimentoFim(LocalDateFilter dataVencimentoFim) {
        this.dataVencimentoFim = dataVencimentoFim;
    }
}
