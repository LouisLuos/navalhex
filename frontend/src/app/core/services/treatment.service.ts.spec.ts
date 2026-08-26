import { TestBed } from '@angular/core/testing';
import { TreatmentServiceTs } from './treatment.service.ts';

describe('TreatmentServiceTs', () => {
  let service: TreatmentServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TreatmentServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
