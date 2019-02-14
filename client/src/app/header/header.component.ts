import {
  Component,
  OnInit,
  ViewChild,
  ViewContainerRef,
  ChangeDetectorRef,
  ElementRef,
  ContentChild
} from "@angular/core";
import { WindowScrollListenerService } from "../window-scroll-listener.service";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"]
})
export class HeaderComponent implements OnInit {
  highlightSelector: string = "highlight-false";

  constructor(
    private windowScrollListener: WindowScrollListenerService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {}

  @ViewChild("mainToolbar", { read: ElementRef })
  mainToolbar: ElementRef;
  @ViewChild("headerHero", { read: ElementRef })
  headerHero: ElementRef;

  ngAfterViewInit() {
    this.getScrollPosition();
  }

  getScrollPosition(): void {
    this.windowScrollListener.getCurrentScrollPosition().subscribe(position => {
      let headerHeight: number = this.headerHero.nativeElement.children[0]
        .offsetHeight;

      let mainToolbarHeight: number = this.mainToolbar.nativeElement.children[0]
        .offsetHeight;
      let threadHold: number = headerHeight - mainToolbarHeight;

      this.highlightSelector = "highlight-" + (position >= threadHold);
    });
  }
}
