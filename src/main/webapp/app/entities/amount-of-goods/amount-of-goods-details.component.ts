import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAmountOfGoods } from '@/shared/model/amount-of-goods.model';
import AmountOfGoodsService from './amount-of-goods.service';

@Component
export default class AmountOfGoodsDetails extends Vue {
  @Inject('amountOfGoodsService') private amountOfGoodsService: () => AmountOfGoodsService;
  public amountOfGoods: IAmountOfGoods = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amountOfGoodsId) {
        vm.retrieveAmountOfGoods(to.params.amountOfGoodsId);
      }
    });
  }

  public retrieveAmountOfGoods(amountOfGoodsId) {
    this.amountOfGoodsService()
      .find(amountOfGoodsId)
      .then(res => {
        this.amountOfGoods = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
