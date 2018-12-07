import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RelatorioLancamentosComponent } from './relatorio-lancamentos/relatorio-lancamentos.component';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';
import { JhiResolvePagingParams } from 'ng-jhipster';

const routes: Routes = [
    {
        path: 'lancamentos',
        component: RelatorioLancamentosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relatórios'
        },
        canActivate: [UserRouteAccessService]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class RelatoriosRoutingModule {}
