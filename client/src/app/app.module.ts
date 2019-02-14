import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { FlexLayoutModule } from "@angular/flex-layout";
import {
  MatInputModule,
  MatGridListModule,
  MatCardModule,
  MatButtonModule,
  MatListModule
} from "@angular/material";
import { BooklibraryDashboardComponent } from "./booklibrary-dashboard/booklibrary-dashboard.component";
import { LayoutModule } from "@angular/cdk/layout";
import { MatSelectModule } from "@angular/material/select";
import { AppShellComponent } from "./app-shell/app-shell.component";
import { HeaderComponent } from "./header/header.component";
import { MainToolbarComponent } from "./main-toolbar/main-toolbar.component";
import { HeaderHeroComponent } from "./header-hero/header-hero.component";
import { SearchBarComponent } from "./search-bar/search-bar.component";

@NgModule({
  declarations: [
    AppComponent,
    BooklibraryDashboardComponent,
    AppShellComponent,
    HeaderComponent,
    MainToolbarComponent,
    HeaderHeroComponent,
    SearchBarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule,
    MatFormFieldModule,
    FlexLayoutModule,
    MatInputModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    LayoutModule,
    MatListModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
