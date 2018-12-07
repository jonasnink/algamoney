import { Component, OnInit } from '@angular/core';
import { RelatoriosService } from '../relatorios.service';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-relatorio-lancamentos',
    templateUrl: './relatorio-lancamentos.component.html',
    styles: []
})
export class RelatorioLancamentosComponent implements OnInit {
    periodoInicio: Date;
    periodoFim: Date;

    constructor(private relatoriosService: RelatoriosService) {}

    ngOnInit() {}

    gerar() {
        console.log('teste de gerar relatorio');
        console.log(this.relatoriosService);

        this.relatoriosService.relatorioLancamentosPorPessoa(this.periodoInicio, this.periodoFim).subscribe(
            res => {
                console.log(res.body);
                const url = window.URL.createObjectURL(res.body);
                window.open(url);
            },
            res => {
                console.log(res);
            }
        );
    }
}
