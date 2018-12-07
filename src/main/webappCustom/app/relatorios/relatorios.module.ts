import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RelatoriosRoutingModule } from './relatorios-routing.module';
import { RelatorioLancamentosComponent } from './relatorio-lancamentos/relatorio-lancamentos.component';
import { AlgamoneySharedModule } from '../shared/shared.module';

@NgModule({
    imports: [CommonModule, FormsModule, RelatoriosRoutingModule, AlgamoneySharedModule],
    declarations: [RelatorioLancamentosComponent]
})
export class RelatoriosModule {}
