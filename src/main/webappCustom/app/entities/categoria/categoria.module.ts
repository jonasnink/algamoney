import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgamoneySharedModule } from 'app/shared';
import {
    CategoriaComponent,
    CategoriaDetailComponent,
    CategoriaUpdateComponent,
    CategoriaDeletePopupComponent,
    CategoriaDeleteDialogComponent,
    categoriaRoute,
    categoriaPopupRoute
} from './';

const ENTITY_STATES = [...categoriaRoute, ...categoriaPopupRoute];

@NgModule({
    imports: [AlgamoneySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoriaComponent,
        CategoriaDetailComponent,
        CategoriaUpdateComponent,
        CategoriaDeleteDialogComponent,
        CategoriaDeletePopupComponent
    ],
    entryComponents: [CategoriaComponent, CategoriaUpdateComponent, CategoriaDeleteDialogComponent, CategoriaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgamoneyCategoriaModule {}
