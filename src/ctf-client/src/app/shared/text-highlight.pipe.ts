import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textHighlight'
})
export class TextHighlightPipe implements PipeTransform {

  transform(value: string, args: any): unknown {
    
    if(!args) {
      return value;
    }
    
    const re = new RegExp(args, 'gi');
    value= value.replace(re, '<mark>$&</mark>');
  
    return value;
  }

}
