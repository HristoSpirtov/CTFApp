import { Pipe, PipeTransform } from '@angular/core';


//custom pipe to replace line breaks to markup for markdown preview
//TODO : implement linebreak when reach textarea 

@Pipe({
  name: 'lineBreaks'
})
export class LineBreaksPipe implements PipeTransform {

  transform(value: string): string {
    return value.replace(/\n/g, '<br />');
  }

}
