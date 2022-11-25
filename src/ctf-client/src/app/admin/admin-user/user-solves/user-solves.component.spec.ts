import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSolvesComponent } from './user-solves.component';

describe('UserSolvesComponent', () => {
  let component: UserSolvesComponent;
  let fixture: ComponentFixture<UserSolvesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSolvesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSolvesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
