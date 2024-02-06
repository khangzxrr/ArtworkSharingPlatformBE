import { IUser } from 'app/shared/model/user.model';
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';
import { RequestBidStatus } from 'app/shared/model/enumerations/request-bid-status.model';

export interface IRequestBidMySuffix {
  id?: number;
  description?: string | null;
  price?: number | null;
  duration?: number | null;
  status?: keyof typeof RequestBidStatus | null;
  user?: IUser | null;
  request?: IRequestMySuffix | null;
}

export const defaultValue: Readonly<IRequestBidMySuffix> = {};
