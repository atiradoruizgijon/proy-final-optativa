import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Planificacion } from './planificacion';

describe('Planificacion', () => {
  let component: Planificacion;
  let fixture: ComponentFixture<Planificacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Planificacion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Planificacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
