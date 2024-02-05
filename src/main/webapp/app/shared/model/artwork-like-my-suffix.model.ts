import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';

export interface IArtworkLikeMySuffix {
  id?: number;
  createAt?: dayjs.Dayjs | null;
  owner?: IUser | null;
  artwork?: IArtworkMySuffix | null;
}

export const defaultValue: Readonly<IArtworkLikeMySuffix> = {};
