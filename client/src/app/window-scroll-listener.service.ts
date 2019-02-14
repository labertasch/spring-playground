import { Injectable } from "@angular/core";
import { Observable, of, BehaviorSubject, Subject, interval } from "rxjs";
import { throttle, debounceTime } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class WindowScrollListenerService {
  scrollPosition = new Subject<number>();

  constructor() {
    window.addEventListener("scroll", this.updatePosition, true);
  }

  updatePosition = (event: any) => {
    this.scrollPosition.next(window.pageYOffset);
  };

  getCurrentScrollPosition(): Observable<number> {
    return this.scrollPosition; //.pipe(debounceTime(0));
  }
}
