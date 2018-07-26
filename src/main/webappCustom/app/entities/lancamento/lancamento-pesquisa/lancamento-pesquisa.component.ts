import { Component, OnInit } from '@angular/core';
import { ILancamento } from '../../../shared/model/lancamento.model';
import { LancamentoService } from '../lancamento.service';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Principal } from '../../../core/auth/principal.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Rx';
import { ITEMS_PER_PAGE } from '../../../shared/constants/pagination.constants';

@Component({
    selector: 'jhi-lancamento-pesquisa',
    templateUrl: './lancamento-pesquisa.component.html',
    styles: []
})
export class LancamentoPesquisaComponent implements OnInit {
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

    constructor(
        private lancamentoService: LancamentoService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams ? data.pagingParams.page : 1;
            this.previousPage = data.paginParams ? data.pagingParams.page : 0;
            this.reverse = data.paginParams ? data.pagingParams.ascending : false;
            this.predicate = data.paginParams ? data.pagingParams.predicate : false;
        });
    }
    getLancamentosAll(descricao?: String) {
        this.lancamentoService
            .queryResumo({
                page: this.page - 1,
                size: this.itemsPerPage,
                descricao: descricao
            })
            .subscribe(
                (res: HttpResponse<ILancamento[]>) => this.paginateLancamentos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private paginateLancamentos(data: ILancamento[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.lancamentos = data;
        console.log(data);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        // console.log('===========>>>>>>>');
        // console.log(result);
        return result;
    }

    ngOnInit() {
        this.getLancamentosAll();
    }
}
