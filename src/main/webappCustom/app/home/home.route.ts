import { Route } from '@angular/router';

import { HomeComponent } from './';
import { LancamentoPesquisaComponent } from '../entities/lancamento/lancamento-pesquisa/lancamento-pesquisa.component';
import { LancamentoComponent } from '../entities/lancamento/lancamento.component';
import { PessoaComponent } from '../entities/pessoa/pessoa.component';

export const HOME_ROUTE: Route = {
    path: '',
    // component: HomeComponent,
    component: LancamentoPesquisaComponent,
    // component: LancamentoComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
