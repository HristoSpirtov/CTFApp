export class User {
    public id: string;
    public school: string;
    public username: string;
    public password: string;
    public email: string;
    public roles: [];

    constructor() {
        this.id = '';
        this.school = '';
        this.username = '';
        this.password = '';
        this.email = '';
        this.roles = [];
    }
    
}
