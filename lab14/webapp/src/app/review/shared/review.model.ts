import {User} from '../../user/shared/user.model';
import {Song} from '../../song/shared/song.model';

export class Review {
  id: number;
  user: User;
  song: Song;
  stars: number;
  reviewText: string;
}
