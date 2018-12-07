import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AlgamoneyEnderecoModule } from './endereco/endereco.module';
import { AlgamoneyPessoaModule } from './pessoa/pessoa.module';
import { AlgamoneyCategoriaModule } from './categoria/categoria.module';
import { AlgamoneyLancamentoModule } from './lancamento/lancamento.module';
import { AlgamoneyContatoModule } from './contato/contato.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AlgamoneyEnderecoModule,
        AlgamoneyPessoaModule,
        AlgamoneyCategoriaModule,
        AlgamoneyLancamentoModule,
        AlgamoneyContatoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgamoneyEntityModule {}
