import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';

export interface IArtworkCommentMySuffix {
  id?: number;
  content?: string | null;
  createAt?: dayjs.Dayjs | null;
  owner?: IUser | null;
  artwork?: IArtworkMySuffix | null;
}

export const defaultValue: Readonly<IArtworkCommentMySuffix> = {};
