import { IContato } from 'app/shared/model//contato.model';

export interface IPessoa {
    id?: number;
    nome?: string;
    ativo?: boolean;
    fotoContentType?: string;
    foto?: any;
    email?: string;
    imgArquivoContentType?: string;
    imgArquivo?: any;
    arquivoContentType?: string;
    arquivo?: any;
    enderecoNumero?: string;
    enderecoId?: number;
    contatos?: IContato[];
}

export class Pessoa implements IPessoa {
    constructor(
        public id?: number,
        public nome?: string,
        public ativo?: boolean,
        public fotoContentType?: string,
        public foto?: any,
        public email?: string,
        public imgArquivoContentType?: string,
        public imgArquivo?: any,
        public arquivoContentType?: string,
        public arquivo?: any,
        public enderecoNumero?: string,
        public enderecoId?: number,
        public contatos?: IContato[]
    ) {
        this.ativo = this.ativo || false;
    }
}
