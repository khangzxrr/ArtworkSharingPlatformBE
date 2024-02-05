import { IUser } from 'app/shared/model/user.model';
import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';

export interface IWalletMySuffix {
  id?: number;
  amount?: number | null;
  user?: IUser | null;
  transactions?: IWalletTransactionMySuffix[] | null;
}

export const defaultValue: Readonly<IWalletMySuffix> = {};
