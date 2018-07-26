export interface IPessoa {
    id?: number;
    nome?: string;
    ativo?: boolean;
    enderecoNumero?: string;
    enderecoId?: number;
}

export class Pessoa implements IPessoa {
    constructor(
        public id?: number,
        public nome?: string,
        public ativo?: boolean,
        public enderecoNumero?: string,
        public enderecoId?: number
    ) {
        this.ativo = false;
    }
}
