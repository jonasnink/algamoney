import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LancamentoService } from './lancamento.service';
import { LancamentoComponent } from './lancamento.component';
import { LancamentoDetailComponent } from './lancamento-detail.component';
import { LancamentoUpdateComponent } from './lancamento-update.component';
import { LancamentoDeletePopupComponent } from './lancamento-delete-dialog.component';
import { LancamentoResumoComponent } from './resumo/lancamento-resumo.component';
import { ILancamento, Lancamento } from '../../shared/model/lancamento.model';

@Injectable({ providedIn: 'root' })
export class LancamentoResolve implements Resolve<ILancamento> {
    constructor(private service: LancamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((lancamento: HttpResponse<Lancamento>) => lancamento.body));
        }
        return of(new Lancamento());
    }
}

export const lancamentoRoute: Routes = [
    {
        path: 'lancamento',
        component: LancamentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            defaultSort: 'id,asc',
            pageTitle: 'algamoneyApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento-resumo',
        component: LancamentoResumoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            defaultSort: 'descricao,asc',
            pageTitle: 'Pesquisa de Lançamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/:id/view',
        component: LancamentoDetailComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/new',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/:id/edit',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoPopupRoute: Routes = [
    {
        path: 'lancamento/:id/delete',
        component: LancamentoDeletePopupComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
