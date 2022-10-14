import { Submission } from './submission';
import { User } from './user';
export class Challenge {
    public id : string;
    public name : string;
    public description : string;
    public value : number;
    public flag : string;
    public participants : User[]
    public submissions : Submission[];
    public type : string;
    public state : string;

    constructor() {
        this.id = '';
        this.type = '';
        this.submissions = [];
        this.name = '';
        this.description = '';
        this.value = 0;
        this.flag = '';
        this.participants = [];
        this.state = "";
    }

    
}
