import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-pessoa-update',
    templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
    private _pessoa: IPessoa;
    isSaving: boolean;

    enderecos: IEndereco[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private pessoaService: PessoaService,
        private enderecoService: EnderecoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pessoa }) => {
            this.pessoa = pessoa;
        });
        this.enderecoService.query().subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                this.enderecos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.pessoa, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pessoa.id !== undefined) {
            this.subscribeToSaveResponse(this.pessoaService.update(this.pessoa));
        } else {
            this.subscribeToSaveResponse(this.pessoaService.create(this.pessoa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>) {
        result.subscribe((res: HttpResponse<IPessoa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }
    get pessoa() {
        return this._pessoa;
    }

    set pessoa(pessoa: IPessoa) {
        this._pessoa = pessoa;
    }
}
