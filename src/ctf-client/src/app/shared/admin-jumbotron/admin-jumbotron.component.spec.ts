import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminJumbotronComponent } from './admin-jumbotron.component';

describe('AdminJumbotronComponent', () => {
  let component: AdminJumbotronComponent;
  let fixture: ComponentFixture<AdminJumbotronComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminJumbotronComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminJumbotronComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
