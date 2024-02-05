import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';

export interface IArtworkAssetMySuffix {
  id?: number;
  media?: IMediaMySuffix | null;
  artwork?: IArtworkMySuffix | null;
}

export const defaultValue: Readonly<IArtworkAssetMySuffix> = {};
