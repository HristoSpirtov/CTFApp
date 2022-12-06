import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAwardsComponent } from './user-awards.component';

describe('UserAwardsComponent', () => {
  let component: UserAwardsComponent;
  let fixture: ComponentFixture<UserAwardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAwardsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAwardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
