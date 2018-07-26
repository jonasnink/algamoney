export interface IEndereco {
    id?: number;
    logradouro?: string;
    numero?: string;
    complemento?: string;
    bairro?: string;
    cep?: string;
    cidade?: string;
    estado?: string;
}

export class Endereco implements IEndereco {
    constructor(
        public id?: number,
        public logradouro?: string,
        public numero?: string,
        public complemento?: string,
        public bairro?: string,
        public cep?: string,
        public cidade?: string,
        public estado?: string
    ) {}
}
