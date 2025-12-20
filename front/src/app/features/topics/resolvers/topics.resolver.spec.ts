import { TestBed } from '@angular/core/testing';

import { TopicsResolver } from './topics.resolver';

describe('TopicsResolver', () => {
  let resolver: TopicsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TopicsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
