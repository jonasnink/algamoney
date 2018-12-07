export interface IContato {
    id?: number;
    codigo?: number;
    nome?: string;
    email?: string;
    telefone?: string;
    teste?: string;
    teste2?: string;
    teste3?: string;
    pessoaNome?: string;
    pessoaId?: number;
}

export class Contato implements IContato {
    constructor(
        public id?: number,
        public codigo?: number,
        public nome?: string,
        public email?: string,
        public telefone?: string,
        public teste?: string,
        public teste2?: string,
        public teste3?: string,
        public pessoaNome?: string,
        public pessoaId?: number
    ) {}
}
