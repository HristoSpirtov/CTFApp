import { Submission } from './submission';
export class Challenge {
    public name : string;
    public description : string;
    public value : number;
    public flag : string;
    public submissions : Submission[];

    constructor() {
        this.submissions = [];
        this.name = '';
        this.description = '';
        this.value = 0;
        this.flag = '';
    }

    
}
