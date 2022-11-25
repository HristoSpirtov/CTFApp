export class Solve {
   
    public id : string;
    public user : string;
    public challenge : string;
    public school : string;
    public type : string;
    public provided : string;
    public date : Date;
    public challengeId : string;
    public value : number

    constructor() {
        this.value = -1;
        this.challengeId = '';
        this.id = '';
        this.user = '';
        this.school = '';
        this.challenge = '';
        this.type = '';
        this.provided = '';
        this.date = new Date();
    }
}