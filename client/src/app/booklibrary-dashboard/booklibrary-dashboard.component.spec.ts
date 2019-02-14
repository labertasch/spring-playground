
import { fakeAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooklibraryDashboardComponent } from './booklibrary-dashboard.component';

describe('BooklibraryDashboardComponent', () => {
  let component: BooklibraryDashboardComponent;
  let fixture: ComponentFixture<BooklibraryDashboardComponent>;

  beforeEach(fakeAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BooklibraryDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BooklibraryDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
