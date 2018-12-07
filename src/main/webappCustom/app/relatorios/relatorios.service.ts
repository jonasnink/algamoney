import { Injectable } from '@angular/core';
import * as moment from 'moment';

// import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { createRequestOption } from 'app/shared';

@Injectable()
export class RelatoriosService {
    lancamentosUrl: string;

    constructor(private http: HttpClient) {
        this.lancamentosUrl = `http://localhost:8080/api/custom/lancamentos`;
    }

    relatorioLancamentosPorPessoa(inicio: Date, fim: Date) {
        let req = {
            inicio: moment(inicio).format('YYYY-MM-DD'),
            fim: moment(fim).format('YYYY-MM-DD')
        };
        const options = createRequestOption(req);
        return this.http.get(`${this.lancamentosUrl}/relatorios/por-pessoa`, {
            params: options,
            observe: 'response',
            responseType: 'blob'
        });
    }
}
