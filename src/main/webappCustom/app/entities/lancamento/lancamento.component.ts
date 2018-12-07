import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Principal } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { LancamentoService } from './lancamento.service';
import { LancamentoCriteria } from '../../shared/model/criteria/lancamento-filter.model';
import { TiposCriteria } from '../../shared/model/criteria/criteria.model';
import { ILancamento } from '../../shared/model/lancamento.model';

@Component({
    selector: 'jhi-lancamento',
    templateUrl: './lancamento.component.html'
})
export class LancamentoComponent implements OnInit, OnDestroy {
    currentAccount: any;
    lancamentos: ILancamento[];
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
    tiposCriteria: TiposCriteria;

    lancamentoCriteria = new LancamentoCriteria();

    constructor(
        private lancamentoService: LancamentoService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        protected router: Router,
        private eventManager: JhiEventManager
    ) {
        //this.lancamentoCriteria = new LancamentoCriteriaCustom();
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.tiposCriteria = new TiposCriteria();
    }

    filtrar(descricao: string) {
        //this.lancamentoCriteria.descricao.contains = descricao;
        this.lancamentoCriteria.descricao.specified = true;
        this.loadAll();
    }
    limparCriteria() {
        for (let l in this.lancamentoCriteria) {
            for (let c in this.lancamentoCriteria[l]) {
                delete this.lancamentoCriteria[l][c];
            }
        }
    }

    loadAll() {
        this.lancamentoService
            .query(this.getParametrosRequisicao())
            .subscribe(
                (res: HttpResponse<ILancamento[]>) => this.paginateLancamentos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/lancamento'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        console.log('paginação');
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/lancamento',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();

        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLancamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILancamento) {
        return item.id;
    }

    registerChangeInLancamentos() {
        this.eventSubscriber = this.eventManager.subscribe('lancamentoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateLancamentos(data: ILancamento[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.lancamentos = data;

        console.log('debug: data: ', data);
        console.log('debug: lancamentos :', this.lancamentos);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private getParametrosRequisicao(): any {
        let req = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        for (let l in this.lancamentoCriteria) {
            if (this.lancamentoCriteria[l]) {
                for (let c in this.lancamentoCriteria[l]) {
                    if (c !== 'constructor') {
                        let prop = l + '.' + c;
                        let value = this.lancamentoCriteria[l][c];
                        req[prop] = value;
                    }
                }
            }
        }
        return req;
    }
}
