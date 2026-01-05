import { TestBed } from '@angular/core/testing';

import { FormValidationErrorService } from './form-validation-error.service';

describe('FormValidationErrorService', () => {
  let service: FormValidationErrorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormValidationErrorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
