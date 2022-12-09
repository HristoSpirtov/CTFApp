export class Award {
    public id : string
    public name : string;
    public description : string;
    public date : Date;
    public value : number;
    public category : string;
    public icon : string;
    public userId : string;

    constructor() {
        this.id = '';
        this.name = '';
        this.description = '';
        this.date = new Date();
        this.value = 0;;
        this.category = '';
        this.icon = '';
        this.userId = '';
    }
}