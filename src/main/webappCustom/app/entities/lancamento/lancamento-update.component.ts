import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { LancamentoService } from './lancamento.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';
import { FileItem, FileUploader, ParsedResponseHeaders } from 'ng2-file-upload';
import { ILancamento } from '../../shared/model/lancamento.model';

@Component({
    selector: 'jhi-lancamento-update',
    templateUrl: './lancamento-update.component.html'
})
export class LancamentoUpdateComponent implements OnInit {
    uploader: FileUploader;

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
        this.uploader = new FileUploader({ url: 'http://localhost:8080/api/custom/lancamentos/anexo' });

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
    removerAnexo() {
        this.lancamento.anexo = null;
    }

    downloadComprovante() {
        this.lancamentoService.downloadComprovante(this.lancamento.anexo).subscribe(
            res => {
                console.log(res.body);
                const url = window.URL.createObjectURL(res.body);
                window.open(url);
            },
            res => {
                console.log('erro: ', res);
            }
        );
    }
    fileSelected() {
        this.uploader.onBeforeUploadItem = (fileItem: FileItem) => {
            this.uploader.authTokenHeader = localStorage.getItem('token');
            console.log('fileItem:', fileItem);
            // fileItem.withCredentials = true;
            // fileItem._xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
            // this.uploader.queue[0]._xhr.setRequestHeader('Authorization', 'Bearer '+localStorage.getItem('token'));
        };
    }
    save() {
        console.log('debug=>', this.uploader);
        if (this.uploader.queue.length) {
            let item = this.uploader.queue.length - 1;
            this.uploader.uploadItem(this.uploader.queue[item]);
            this.uploader.queue[item].onSuccess = (response: string, status: number, headers: ParsedResponseHeaders) => {
                this.lancamento.anexo = response;
                this.saveLancamento();
            };
            this.uploader.queue[item].onError = (response: string, status: number, headers: ParsedResponseHeaders) => {
                console.log('Erro ao Processar Envio de Arquivo: Response => ', response, '; status => ', status, '; headers => ', headers);
            };
        } else {
            this.saveLancamento();
        }
    }

    saveLancamento() {
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
