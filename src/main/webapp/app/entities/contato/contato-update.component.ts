import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IContato } from 'app/shared/model/contato.model';
import { ContatoService } from './contato.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-contato-update',
    templateUrl: './contato-update.component.html'
})
export class ContatoUpdateComponent implements OnInit {
    private _contato: IContato;
    isSaving: boolean;

    pessoas: IPessoa[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private contatoService: ContatoService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contato }) => {
            this.contato = contato;
        });
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
        if (this.contato.id !== undefined) {
            this.subscribeToSaveResponse(this.contatoService.update(this.contato));
        } else {
            this.subscribeToSaveResponse(this.contatoService.create(this.contato));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContato>>) {
        result.subscribe((res: HttpResponse<IContato>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
    get contato() {
        return this._contato;
    }

    set contato(contato: IContato) {
        this._contato = contato;
    }
}
