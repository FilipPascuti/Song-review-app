import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {UserComponent} from './user/user.component';
import {ArtistComponent} from './artist/artist.component';
import {SongComponent} from './song/song.component';
import {ReviewComponent} from './review/review.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {UserAddComponent} from './user/user-add/user-add.component';
import {UserUpdateComponent} from './user/user-update/user-update.component';
import {UserDeleteComponent} from './user/user-delete/user-delete.component';
import {ArtistListComponent} from './artist/artist-list/artist-list.component';
import {ArtistAddComponent} from './artist/artist-add/artist-add.component';
import {ArtistUpdateComponent} from './artist/artist-update/artist-update.component';
import {ArtistDeleteComponent} from './artist/artist-delete/artist-delete.component';
import {ArtistDetailsComponent} from './artist/artist-details/artist-details.component';
import {UserFilterComponent} from './user/user-filter/user-filter.component';
import {UserSortedComponent} from './user/user-sorted/user-sorted.component';
import {ArtistFilterComponent} from './artist/artist-filter/artist-filter.component';
import {ArtistSortedComponent} from './artist/artist-sorted/artist-sorted.component';
import {SongListComponent} from './song/song-list/song-list.component';
import {SongAddComponent} from './song/song-add/song-add.component';
import {SongUpdateComponent} from './song/song-update/song-update.component';
import {SongDeleteComponent} from './song/song-delete/song-delete.component';
import {SongDetailsComponent} from './song/song-details/song-details.component';
import {ReviewListComponent} from './review/review-list/review-list.component';
import {ReviewAddComponent} from './review/review-add/review-add.component';
import {ReviewDeleteComponent} from './review/review-delete/review-delete.component';
import {ReviewUpdateComponent} from './review/review-update/review-update.component';
import {ReviewDetailsComponent} from './review/review-details/review-details.component';
import {UserStatisticComponent} from './review/user-statistic/user-statistic.component';
import {SongStatisticComponent} from './review/song-statistic/song-statistic.component';


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'users', component: UserComponent},
  {path: 'users/list', component: UserListComponent},
  {path: 'users/filter', component: UserFilterComponent},
  {path: 'users/sort', component: UserSortedComponent},
  {path: 'users/add', component: UserAddComponent},
  {path: 'users/update', component: UserUpdateComponent},
  {path: 'users/delete', component: UserDeleteComponent},
  {path: 'artists', component: ArtistComponent},
  {path: 'artists/list', component: ArtistListComponent},
  {path: 'artists/filter', component: ArtistFilterComponent},
  {path: 'artists/sort', component: ArtistSortedComponent},
  {path: 'artists/details/:id', component: ArtistDetailsComponent},
  {path: 'artists/add', component: ArtistAddComponent},
  {path: 'artists/update/:id', component: ArtistUpdateComponent},
  {path: 'artists/delete/:id', component: ArtistDeleteComponent},
  {path: 'songs', component: SongComponent},
  {path: 'songs/list', component: SongListComponent},
  {path: 'songs/add/:id', component: SongAddComponent},
  {path: 'songs/update/:id', component: SongUpdateComponent},
  {path: 'songs/delete/:id', component: SongDeleteComponent},
  {path: 'songs/details/:id', component: SongDetailsComponent},
  {path: 'reviews', component: ReviewComponent},
  {path: 'reviews/list', component: ReviewListComponent},
  {path: 'reviews/add/:songId', component: ReviewAddComponent},
  {path: 'reviews/details/:userId/:songId', component: ReviewDetailsComponent},
  {path: 'reviews/delete/:userId/:songId', component: ReviewDeleteComponent},
  {path: 'reviews/update/:userId/:songId', component: ReviewUpdateComponent},
  {path: 'reviews/user-statistic', component: UserStatisticComponent},
  {path: 'reviews/song-statistic', component: SongStatisticComponent},
  {path: '**', redirectTo: 'home'},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
