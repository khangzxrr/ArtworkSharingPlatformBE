import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';

export interface IRequestAttachmentMySuffix {
  id?: number;
  media?: IMediaMySuffix | null;
  request?: IRequestMySuffix | null;
}

export const defaultValue: Readonly<IRequestAttachmentMySuffix> = {};
