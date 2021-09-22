import { Component, OnInit } from '@angular/core';
import {UserStatistic} from '../shared/statistics-classes/userStatistic';
import {SongStatistic} from '../shared/statistics-classes/songStatistic';
import {ReviewService} from '../shared/review.service';

@Component({
  selector: 'app-song-statistic',
  templateUrl: './song-statistic.component.html',
  styleUrls: ['./song-statistic.component.css']
})
export class SongStatisticComponent implements OnInit {

  songs: SongStatistic[];
  displayedColumns = ['song', 'average'];

  constructor(private service: ReviewService) { }

  ngOnInit(): void {
    this.getSongs();
  }

  getSongs(): void {
    this.service.getSongAverages().subscribe(
      songs => this.songs = songs
    );
  }
}
