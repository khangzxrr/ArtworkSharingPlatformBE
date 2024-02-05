import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';

export interface IArtworkCategoryMySuffix {
  id?: number;
  name?: string | null;
  artworks?: IArtworkMySuffix[] | null;
}

export const defaultValue: Readonly<IArtworkCategoryMySuffix> = {};
