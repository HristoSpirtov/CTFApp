import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEditChallengeComponent } from './admin-edit-challenge.component';

describe('AdminEditChallengeComponent', () => {
  let component: AdminEditChallengeComponent;
  let fixture: ComponentFixture<AdminEditChallengeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminEditChallengeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminEditChallengeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
