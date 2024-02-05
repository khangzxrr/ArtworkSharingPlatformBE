import { IUser } from 'app/shared/model/user.model';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { ComplainStatus } from 'app/shared/model/enumerations/complain-status.model';

export interface IArtworkComplainMySuffix {
  id?: number;
  content?: string | null;
  status?: keyof typeof ComplainStatus | null;
  user?: IUser | null;
  artwork?: IArtworkMySuffix | null;
}

export const defaultValue: Readonly<IArtworkComplainMySuffix> = {};
