import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICategoria } from 'app/shared/model/categoria.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CategoriaService } from './categoria.service';

@Component({
    selector: 'jhi-categoria',
    templateUrl: './categoria.component.html'
})
export class CategoriaComponent implements OnInit, OnDestroy {
    currentAccount: any;
    categorias: ICategoria[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private categoriaService: CategoriaService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        console.log('loadAll');
        this.categoriaService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICategoria[]>) => this.paginateCategorias(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        console.log('debug => loadPage');
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        console.log('debug => transition');
        this.router.navigate(['/categoria'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        console.log('debug => clear');
        this.page = 0;
        this.router.navigate([
            '/categoria',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        console.log('debug => ngOnInit', this.principal);
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
            console.log('debug => ngOnInit => ', account);
        });
        this.registerChangeInCategorias();
    }

    ngOnDestroy() {
        console.log('debug => ngOnDestroy');
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategoria) {
        console.log('debug => trackId', item);
        return item.id;
    }

    registerChangeInCategorias() {
        console.log('debug => registerChangeInCategorias');
        this.eventSubscriber = this.eventManager.subscribe('categoriaListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateCategorias(data: ICategoria[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.categorias = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
