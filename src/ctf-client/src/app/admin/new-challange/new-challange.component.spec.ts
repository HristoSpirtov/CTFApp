import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChallangeComponent } from './new-challange.component';

describe('NewChallangeComponent', () => {
  let component: NewChallangeComponent;
  let fixture: ComponentFixture<NewChallangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewChallangeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewChallangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
