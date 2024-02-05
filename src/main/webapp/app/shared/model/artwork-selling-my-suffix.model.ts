import dayjs from 'dayjs';
import { ISellingBidMySuffix } from 'app/shared/model/selling-bid-my-suffix.model';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { ArtworkSellingType } from 'app/shared/model/enumerations/artwork-selling-type.model';
import { ArtworkSellingStatus } from 'app/shared/model/enumerations/artwork-selling-status.model';

export interface IArtworkSellingMySuffix {
  id?: number;
  createAt?: dayjs.Dayjs | null;
  type?: keyof typeof ArtworkSellingType | null;
  status?: keyof typeof ArtworkSellingStatus | null;
  expectedSellingPrice?: number | null;
  bids?: ISellingBidMySuffix[] | null;
  artwork?: IArtworkMySuffix | null;
}

export const defaultValue: Readonly<IArtworkSellingMySuffix> = {};
