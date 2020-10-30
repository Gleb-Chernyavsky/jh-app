import { IGoods } from '@/shared/model/goods.model';

export interface ICategory {
  id?: number;
  category?: string;
  goods?: IGoods[];
}

export class Category implements ICategory {
  constructor(public id?: number, public category?: string, public goods?: IGoods[]) {}
}
