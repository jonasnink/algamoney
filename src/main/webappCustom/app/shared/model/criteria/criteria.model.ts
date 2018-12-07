export class Criteria {
    equals?: any;
    specified?: boolean;
    constructor() {}
}
export class CriteriaString extends Criteria {
    contains?: string;
    constructor() {
        super();
    }
}
export class CriteriaNumberOrDate extends Criteria {
    greaterThan?: any;
    lessThan?: any;
    greaterOrEqualThan?: any;
    lessOrEqualThan?: any;
    constructor() {
        super();
    }
}

export class TiposCriteria {
    criteriaString: string[] = [];
    criteriaNumberOrDate: string[] = [];
    constructor() {
        this.criteriaString.push('equals');
        this.criteriaString.push('specified');
        this.criteriaString.push('contains');

        this.criteriaNumberOrDate.push('equals');
        this.criteriaNumberOrDate.push('specified');
        this.criteriaNumberOrDate.push('greaterThan');
        this.criteriaNumberOrDate.push('lessThan');
        this.criteriaNumberOrDate.push('greaterOrEqualThan');
        this.criteriaNumberOrDate.push('lessOrEqualThan');
    }
}
