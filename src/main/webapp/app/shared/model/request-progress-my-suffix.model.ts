import dayjs from 'dayjs';
import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';
import { IRequestProgressAttachmentMySuffix } from 'app/shared/model/request-progress-attachment-my-suffix.model';
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';
import { RequestProgressType } from 'app/shared/model/enumerations/request-progress-type.model';
import { RequestProgressStatus } from 'app/shared/model/enumerations/request-progress-status.model';

export interface IRequestProgressMySuffix {
  id?: number;
  date?: dayjs.Dayjs | null;
  description?: string | null;
  type?: keyof typeof RequestProgressType | null;
  status?: keyof typeof RequestProgressStatus | null;
  transaction?: IWalletTransactionMySuffix | null;
  attachments?: IRequestProgressAttachmentMySuffix[] | null;
  request?: IRequestMySuffix | null;
}

export const defaultValue: Readonly<IRequestProgressMySuffix> = {};
