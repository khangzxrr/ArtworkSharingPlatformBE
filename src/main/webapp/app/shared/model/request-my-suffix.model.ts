import { IRequestBidMySuffix } from 'app/shared/model/request-bid-my-suffix.model';
import { IRequestProgressMySuffix } from 'app/shared/model/request-progress-my-suffix.model';
import { IRequestAttachmentMySuffix } from 'app/shared/model/request-attachment-my-suffix.model';
import { IUser } from 'app/shared/model/user.model';
import { RequestStatus } from 'app/shared/model/enumerations/request-status.model';

export interface IRequestMySuffix {
  id?: number;
  description?: string | null;
  status?: keyof typeof RequestStatus | null;
  requestBids?: IRequestBidMySuffix[] | null;
  requestProgresses?: IRequestProgressMySuffix[] | null;
  attachments?: IRequestAttachmentMySuffix[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IRequestMySuffix> = {};
