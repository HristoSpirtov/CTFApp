import { Role } from './role';
export class User {
    public id: string;
    public name: string;
    public school: string;
    public username: string;
    public email: string;
    public verified : boolean;
    public hidden  : boolean;
    public banned : boolean;
    public roles : Role[];

    constructor() {
        this.id = '';
        this.verified = false;
        this.hidden = false;
        this.banned = false;
        this.name = '';
        this.school = '';
        this.username = '';
        this.email = '';
        this.roles = [];
    }


    
}
