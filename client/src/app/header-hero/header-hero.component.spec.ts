import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderHeroComponent } from './header-hero.component';

describe('HeaderHeroComponent', () => {
  let component: HeaderHeroComponent;
  let fixture: ComponentFixture<HeaderHeroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderHeroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderHeroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
