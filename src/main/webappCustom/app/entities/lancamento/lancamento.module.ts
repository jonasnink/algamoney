import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
    FileUploadModule,
    FileDropDirective,
    FileItem,
    FileLikeObject,
    FileSelectDirective,
    FileUploader,
    FileUploaderOptions,
    FilterFunction,
    Headers,
    ParsedResponseHeaders
} from 'ng2-file-upload';

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
import { LancamentoResumoComponent } from './resumo/lancamento-resumo.component';

const ENTITY_STATES = [...lancamentoRoute, ...lancamentoPopupRoute];

@NgModule({
    imports: [AlgamoneySharedModule, RouterModule.forChild(ENTITY_STATES), FileUploadModule],
    declarations: [
        LancamentoComponent,
        LancamentoResumoComponent,
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
