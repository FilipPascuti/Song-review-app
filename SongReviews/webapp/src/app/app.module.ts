import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './ap-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { ArtistComponent } from './artist/artist.component';
import { ReviewComponent } from './review/review.component';
import { SongComponent } from './song/song.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserAddComponent } from './user/user-add/user-add.component';
import { UserDeleteComponent } from './user/user-delete/user-delete.component';
import { UserUpdateComponent } from './user/user-update/user-update.component';
import { ArtistListComponent } from './artist/artist-list/artist-list.component';
import { ArtistAddComponent } from './artist/artist-add/artist-add.component';
import { ArtistUpdateComponent } from './artist/artist-update/artist-update.component';
import { ArtistDeleteComponent } from './artist/artist-delete/artist-delete.component';
import { ArtistDetailsComponent } from './artist/artist-details/artist-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserFilterComponent } from './user/user-filter/user-filter.component';
import { UserSortedComponent } from './user/user-sorted/user-sorted.component';
import { ArtistFilterComponent } from './artist/artist-filter/artist-filter.component';
import { ArtistSortedComponent } from './artist/artist-sorted/artist-sorted.component';
import {MatInputModule} from '@angular/material/input';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import { SongListComponent } from './song/song-list/song-list.component';
import { SongAddComponent } from './song/song-add/song-add.component';
import { SongUpdateComponent } from './song/song-update/song-update.component';
import { SongDeleteComponent } from './song/song-delete/song-delete.component';
import { SongDetailsComponent } from './song/song-details/song-details.component';
import {MatSelectModule} from '@angular/material/select';
import { ReviewListComponent } from './review/review-list/review-list.component';
import { ReviewUpdateComponent } from './review/review-update/review-update.component';
import { ReviewDeleteComponent } from './review/review-delete/review-delete.component';
import { ReviewAddComponent } from './review/review-add/review-add.component';
import { ReviewDetailsComponent } from './review/review-details/review-details.component';
import { UserStatisticComponent } from './review/user-statistic/user-statistic.component';
import { SongStatisticComponent } from './review/song-statistic/song-statistic.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UserComponent,
    ArtistComponent,
    ReviewComponent,
    SongComponent,
    UserListComponent,
    UserAddComponent,
    UserDeleteComponent,
    UserUpdateComponent,
    ArtistListComponent,
    ArtistAddComponent,
    ArtistUpdateComponent,
    ArtistDeleteComponent,
    ArtistDetailsComponent,
    UserFilterComponent,
    UserSortedComponent,
    ArtistFilterComponent,
    ArtistSortedComponent,
    SongListComponent,
    SongAddComponent,
    SongUpdateComponent,
    SongDeleteComponent,
    SongDetailsComponent,
    ReviewListComponent,
    ReviewUpdateComponent,
    ReviewDeleteComponent,
    ReviewAddComponent,
    ReviewDetailsComponent,
    UserStatisticComponent,
    SongStatisticComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatTableModule,
    MatToolbarModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
