import {Artist} from '../../artist/shared/artist.model';

export class SongDetails {
  title: string;
  length: number;
  key: string;
}

export class Song {
  id: number;
  details: SongDetails;
  artist: Artist;
}
