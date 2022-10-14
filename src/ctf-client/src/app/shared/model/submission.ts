export class Submission {
   
    public id : string;
    public user : string;
    public challenge : string;
    public school : string;
    public type : string;
    public provided : string;
    public date : Date;

    constructor() {
        this.id = '';
        this.user = '';
        this.school = '';
        this.challenge = '';
        this.type = '';
        this.provided = '';
        this.date = new Date();
    }
}