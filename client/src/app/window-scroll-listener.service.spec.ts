import { TestBed, inject } from '@angular/core/testing';

import { WindowScrollListenerService } from './window-scroll-listener.service';

describe('WindowScrollListenerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WindowScrollListenerService]
    });
  });

  it('should be created', inject([WindowScrollListenerService], (service: WindowScrollListenerService) => {
    expect(service).toBeTruthy();
  }));
});
