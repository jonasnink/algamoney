import { CriteriaNumberOrDate, CriteriaString } from './criteria.model';

export interface ILancamentoCriteria {
    id?: CriteriaNumberOrDate;
    descricao?: CriteriaString;
    dataVencimento?: CriteriaNumberOrDate;
    dataPagamento?: CriteriaNumberOrDate;
    valor?: CriteriaNumberOrDate;
    observacao?: CriteriaString;
    tipoLancamento?: CriteriaString;
    categoriaId?: CriteriaNumberOrDate;
    pessoaId?: CriteriaNumberOrDate;
}

export class LancamentoCriteria implements ILancamentoCriteria {
    id = new CriteriaNumberOrDate();
    descricao = new CriteriaString();
    dataVencimento = new CriteriaNumberOrDate();
    dataPagamento = new CriteriaNumberOrDate();
    valor = new CriteriaNumberOrDate();
    observacao = new CriteriaString();
    tipoLancamento = new CriteriaString();
    categoriaId = new CriteriaNumberOrDate();
    pessoaId = new CriteriaNumberOrDate();
}

// export class LancamentoCriteriaCustom implements ILancamentoCriteria {
//     constructor(
//         public id = new CriteriaNumberOrDate(),
//         public descricao = new CriteriaString(),
//         public dataVencimento = new CriteriaNumberOrDate(),
//         public dataPagamento = new CriteriaNumberOrDate(),
//         public valor = new CriteriaNumberOrDate(),
//         public observacao = new CriteriaString(),
//         public tipoLancamento = new CriteriaString(),
//         public categoriaId = new CriteriaNumberOrDate(),
//         public pessoaId = new CriteriaNumberOrDate(),
//     ) {}

//}
