import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Kookia } from './kookia';

describe('Kookia', () => {
  let component: Kookia;
  let fixture: ComponentFixture<Kookia>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Kookia]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Kookia);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
