import { IGoods } from '@/shared/model/goods.model';
import { IDeal } from '@/shared/model/deal.model';

export interface IAmountOfGoods {
  id?: number;
  amount?: number;
  name?: string;
  goods?: IGoods;
  deals?: IDeal[];
}

export class AmountOfGoods implements IAmountOfGoods {
  constructor(public id?: number, public amount?: number, public name?: string, public goods?: IGoods, public deals?: IDeal[]) {}
}
