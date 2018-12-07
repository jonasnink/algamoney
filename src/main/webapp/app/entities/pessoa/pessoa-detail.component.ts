import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPessoa } from 'app/shared/model/pessoa.model';

@Component({
    selector: 'jhi-pessoa-detail',
    templateUrl: './pessoa-detail.component.html'
})
export class PessoaDetailComponent implements OnInit {
    pessoa: IPessoa;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pessoa }) => {
            this.pessoa = pessoa;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
