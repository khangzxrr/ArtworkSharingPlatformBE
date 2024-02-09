import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { IRequestProgressMySuffix } from 'app/shared/model/request-progress-my-suffix.model';

export interface IRequestProgressAttachmentMySuffix {
  id?: number;
  media?: IMediaMySuffix | null;
  requestProgress?: IRequestProgressMySuffix | null;
}

export const defaultValue: Readonly<IRequestProgressAttachmentMySuffix> = {};
