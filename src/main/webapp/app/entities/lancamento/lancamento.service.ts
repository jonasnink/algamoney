import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILancamento } from 'app/shared/model/lancamento.model';

type EntityResponseType = HttpResponse<ILancamento>;
type EntityArrayResponseType = HttpResponse<ILancamento[]>;

@Injectable({ providedIn: 'root' })
export class LancamentoService {
    private resourceUrl = SERVER_API_URL + 'api/lancamentos';

    constructor(private http: HttpClient) {}

    create(lancamento: ILancamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamento);
        return this.http
            .post<ILancamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lancamento: ILancamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamento);
        return this.http
            .put<ILancamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILancamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILancamento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(lancamento: ILancamento): ILancamento {
        const copy: ILancamento = Object.assign({}, lancamento, {
            dataVencimento:
                lancamento.dataVencimento != null && lancamento.dataVencimento.isValid()
                    ? lancamento.dataVencimento.format(DATE_FORMAT)
                    : null,
            dataPagamento:
                lancamento.dataPagamento != null && lancamento.dataPagamento.isValid() ? lancamento.dataPagamento.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataVencimento = res.body.dataVencimento != null ? moment(res.body.dataVencimento) : null;
        res.body.dataPagamento = res.body.dataPagamento != null ? moment(res.body.dataPagamento) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((lancamento: ILancamento) => {
            lancamento.dataVencimento = lancamento.dataVencimento != null ? moment(lancamento.dataVencimento) : null;
            lancamento.dataPagamento = lancamento.dataPagamento != null ? moment(lancamento.dataPagamento) : null;
        });
        return res;
    }
}
