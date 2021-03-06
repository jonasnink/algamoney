import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Contato } from 'app/shared/model/contato.model';
import { ContatoService } from './contato.service';
import { ContatoComponent } from './contato.component';
import { ContatoDetailComponent } from './contato-detail.component';
import { ContatoUpdateComponent } from './contato-update.component';
import { ContatoDeletePopupComponent } from './contato-delete-dialog.component';
import { IContato } from 'app/shared/model/contato.model';

@Injectable({ providedIn: 'root' })
export class ContatoResolve implements Resolve<IContato> {
    constructor(private service: ContatoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((contato: HttpResponse<Contato>) => contato.body));
        }
        return of(new Contato());
    }
}

export const contatoRoute: Routes = [
    {
        path: 'contato',
        component: ContatoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'algamoneyApp.contato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contato/:id/view',
        component: ContatoDetailComponent,
        resolve: {
            contato: ContatoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.contato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contato/new',
        component: ContatoUpdateComponent,
        resolve: {
            contato: ContatoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.contato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contato/:id/edit',
        component: ContatoUpdateComponent,
        resolve: {
            contato: ContatoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.contato.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contatoPopupRoute: Routes = [
    {
        path: 'contato/:id/delete',
        component: ContatoDeletePopupComponent,
        resolve: {
            contato: ContatoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'algamoneyApp.contato.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
