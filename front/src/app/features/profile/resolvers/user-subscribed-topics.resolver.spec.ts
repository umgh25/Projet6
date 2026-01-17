import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { userSubscribedTopicsResolver } from './user-subscribed-topics.resolver';

describe('userSubscribedTopicsResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => userSubscribedTopicsResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
