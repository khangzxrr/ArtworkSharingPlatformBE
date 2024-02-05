import dayjs from 'dayjs';
import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';
import { IArtworkSellingMySuffix } from 'app/shared/model/artwork-selling-my-suffix.model';
import { SellingBidStatus } from 'app/shared/model/enumerations/selling-bid-status.model';

export interface ISellingBidMySuffix {
  id?: number;
  bidPrice?: number | null;
  createAt?: dayjs.Dayjs | null;
  status?: keyof typeof SellingBidStatus | null;
  transaction?: IWalletTransactionMySuffix;
  artworkSelling?: IArtworkSellingMySuffix | null;
}

export const defaultValue: Readonly<ISellingBidMySuffix> = {};
