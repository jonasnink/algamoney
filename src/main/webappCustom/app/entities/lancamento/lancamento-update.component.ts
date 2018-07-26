import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-lancamento-update',
    templateUrl: './lancamento-update.component.html'
})
export class LancamentoUpdateComponent implements OnInit {
    private _lancamento: ILancamento;
    isSaving: boolean;

    categorias: ICategoria[];

    pessoas: IPessoa[];
    dataVencimentoDp: any;
    dataPagamentoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private lancamentoService: LancamentoService,
        private categoriaService: CategoriaService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
        this.categoriaService.query().subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                this.categorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pessoaService.query().subscribe(
            (res: HttpResponse<IPessoa[]>) => {
                this.pessoas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lancamento.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoService.update(this.lancamento));
        } else {
            this.subscribeToSaveResponse(this.lancamentoService.create(this.lancamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILancamento>>) {
        result.subscribe((res: HttpResponse<ILancamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCategoriaById(index: number, item: ICategoria) {
        return item.id;
    }

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
    get lancamento() {
        return this._lancamento;
    }

    set lancamento(lancamento: ILancamento) {
        this._lancamento = lancamento;
    }
}
