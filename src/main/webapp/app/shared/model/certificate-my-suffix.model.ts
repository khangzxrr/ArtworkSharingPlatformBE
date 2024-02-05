import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { IUser } from 'app/shared/model/user.model';

export interface ICertificateMySuffix {
  id?: number;
  description?: string | null;
  media?: IMediaMySuffix | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ICertificateMySuffix> = {};
