import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFailsComponent } from './user-fails.component';

describe('UserFailsComponent', () => {
  let component: UserFailsComponent;
  let fixture: ComponentFixture<UserFailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserFailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
