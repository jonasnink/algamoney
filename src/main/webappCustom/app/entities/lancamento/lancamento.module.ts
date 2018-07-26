import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgamoneySharedModule } from 'app/shared';
import {
    LancamentoComponent,
    LancamentoDetailComponent,
    LancamentoUpdateComponent,
    LancamentoDeletePopupComponent,
    LancamentoDeleteDialogComponent,
    lancamentoRoute,
    lancamentoPopupRoute
} from './';
import { LancamentoPesquisaComponent } from './lancamento-pesquisa/lancamento-pesquisa.component';

const ENTITY_STATES = [...lancamentoRoute, ...lancamentoPopupRoute];

@NgModule({
    imports: [AlgamoneySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LancamentoComponent,
        LancamentoDetailComponent,
        LancamentoUpdateComponent,
        LancamentoDeleteDialogComponent,
        LancamentoDeletePopupComponent,
        LancamentoPesquisaComponent
    ],
    entryComponents: [LancamentoComponent, LancamentoUpdateComponent, LancamentoDeleteDialogComponent, LancamentoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgamoneyLancamentoModule {}