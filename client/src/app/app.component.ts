import { Component } from "@angular/core";
import { WindowScrollListenerService } from "./window-scroll-listener.service";
@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  title = "client";
  position = 0;
  constructor(private windowScrollListener: WindowScrollListenerService) {}
}
