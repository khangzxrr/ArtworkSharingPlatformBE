import { IRequestProgressAttachmentMySuffix } from 'app/shared/model/request-progress-attachment-my-suffix.model';
import { IRequestAttachmentMySuffix } from 'app/shared/model/request-attachment-my-suffix.model';
import { IArtworkAssetMySuffix } from 'app/shared/model/artwork-asset-my-suffix.model';
import { ICertificateMySuffix } from 'app/shared/model/certificate-my-suffix.model';

export interface IMediaMySuffix {
  id?: number;
  url?: string | null;
  requestProgressAttachment?: IRequestProgressAttachmentMySuffix | null;
  requestAttachment?: IRequestAttachmentMySuffix | null;
  artworkAsset?: IArtworkAssetMySuffix | null;
  certificate?: ICertificateMySuffix | null;
}

export const defaultValue: Readonly<IMediaMySuffix> = {};
