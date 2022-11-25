import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMissingComponent } from './user-missing.component';

describe('UserMissingComponent', () => {
  let component: UserMissingComponent;
  let fixture: ComponentFixture<UserMissingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserMissingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserMissingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
