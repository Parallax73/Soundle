import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuessCardComponent } from './guess-card.component';

describe('GuessCardComponent', () => {
  let component: GuessCardComponent;
  let fixture: ComponentFixture<GuessCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuessCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GuessCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
