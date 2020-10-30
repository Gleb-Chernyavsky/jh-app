import { IClient } from '@/shared/model/client.model';
import { IAmountOfGoods } from '@/shared/model/amount-of-goods.model';

export interface IDeal {
  id?: number;
  date?: Date;
  client?: IClient;
  amountOfGoods?: IAmountOfGoods[];
}

export class Deal implements IDeal {
  constructor(public id?: number, public date?: Date, public client?: IClient, public amountOfGoods?: IAmountOfGoods[]) {}
}
