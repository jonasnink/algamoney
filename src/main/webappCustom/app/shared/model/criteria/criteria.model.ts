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
