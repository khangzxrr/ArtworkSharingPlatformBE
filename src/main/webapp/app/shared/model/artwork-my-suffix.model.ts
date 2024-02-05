import { IArtworkSellingMySuffix } from 'app/shared/model/artwork-selling-my-suffix.model';
import { IArtworkAssetMySuffix } from 'app/shared/model/artwork-asset-my-suffix.model';
import { IArtworkCommentMySuffix } from 'app/shared/model/artwork-comment-my-suffix.model';
import { IArtworkComplainMySuffix } from 'app/shared/model/artwork-complain-my-suffix.model';
import { IArtworkLikeMySuffix } from 'app/shared/model/artwork-like-my-suffix.model';
import { IUser } from 'app/shared/model/user.model';
import { IArtworkCategoryMySuffix } from 'app/shared/model/artwork-category-my-suffix.model';
import { ArtworkStatus } from 'app/shared/model/enumerations/artwork-status.model';

export interface IArtworkMySuffix {
  id?: number;
  name?: string | null;
  description?: string | null;
  createAt?: string | null;
  status?: keyof typeof ArtworkStatus | null;
  artworkSelling?: IArtworkSellingMySuffix | null;
  artworkAssets?: IArtworkAssetMySuffix[] | null;
  comments?: IArtworkCommentMySuffix[] | null;
  complains?: IArtworkComplainMySuffix[] | null;
  likes?: IArtworkLikeMySuffix[] | null;
  owner?: IUser | null;
  category?: IArtworkCategoryMySuffix | null;
}

export const defaultValue: Readonly<IArtworkMySuffix> = {};
