import { Moment } from 'moment';

export const enum TipoLancamento {
    RECEITA = 'RECEITA',
    DESPESA = 'DESPESA'
}

export interface ILancamento {
    id?: number;
    descricao?: string;
    dataVencimento?: Moment;
    dataPagamento?: Moment;
    valor?: number;
    observacao?: string;
    tipoLancamento?: TipoLancamento;
    categoriaId?: number;
    pessoaId?: number;
}
export class Lancamento implements ILancamento {
    constructor(
        public id?: number,
        public descricao?: string,
        public dataVencimento?: Moment,
        public dataPagamento?: Moment,
        public valor?: number,
        public observacao?: string,
        public tipoLancamento?: TipoLancamento,
        public categoriaId?: number,
        public pessoaId?: number
    ) {}
}
