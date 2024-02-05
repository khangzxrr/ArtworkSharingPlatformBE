import { IArtworkAssetMySuffix } from 'app/shared/model/artwork-asset-my-suffix.model';
import { ICertificateMySuffix } from 'app/shared/model/certificate-my-suffix.model';

export interface IMediaMySuffix {
  id?: number;
  url?: string | null;
  artworkAsset?: IArtworkAssetMySuffix | null;
  certificate?: ICertificateMySuffix | null;
}

export const defaultValue: Readonly<IMediaMySuffix> = {};
