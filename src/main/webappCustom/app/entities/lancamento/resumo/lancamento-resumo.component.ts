import { Component } from '@angular/core';
import { LancamentoComponent } from '../';

@Component({
    selector: 'lancamento-resumo',
    templateUrl: './lancamento-resumo.component.html'
})
export class LancamentoResumoComponent extends LancamentoComponent {
    filtrar(descricao: string) {
        this.lancamentoCriteria.descricao.contains = 'casa';
        this.lancamentoCriteria.descricao.specified = true;
        this.loadAll();
    }
    transition() {
        this.router.navigate(['/lancamento-resumo'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        console.log('paginação resumo');
        this.loadAll();
    }
}
