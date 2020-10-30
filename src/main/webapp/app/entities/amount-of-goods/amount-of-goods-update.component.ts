import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import GoodsService from '../goods/goods.service';
import { IGoods } from '@/shared/model/goods.model';

import DealService from '../deal/deal.service';
import { IDeal } from '@/shared/model/deal.model';

import AlertService from '@/shared/alert/alert.service';
import { IAmountOfGoods, AmountOfGoods } from '@/shared/model/amount-of-goods.model';
import AmountOfGoodsService from './amount-of-goods.service';

const validations: any = {
  amountOfGoods: {
    amount: {},
    name: {},
  },
};

@Component({
  validations,
})
export default class AmountOfGoodsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('amountOfGoodsService') private amountOfGoodsService: () => AmountOfGoodsService;
  public amountOfGoods: IAmountOfGoods = new AmountOfGoods();

  @Inject('goodsService') private goodsService: () => GoodsService;

  public goods: IGoods[] = [];

  @Inject('dealService') private dealService: () => DealService;

  public deals: IDeal[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amountOfGoodsId) {
        vm.retrieveAmountOfGoods(to.params.amountOfGoodsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.amountOfGoods.id) {
      this.amountOfGoodsService()
        .update(this.amountOfGoods)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AmountOfGoods is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.amountOfGoodsService()
        .create(this.amountOfGoods)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AmountOfGoods is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAmountOfGoods(amountOfGoodsId): void {
    this.amountOfGoodsService()
      .find(amountOfGoodsId)
      .then(res => {
        this.amountOfGoods = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.goodsService()
      .retrieve()
      .then(res => {
        this.goods = res.data;
      });
    this.dealService()
      .retrieve()
      .then(res => {
        this.deals = res.data;
      });
  }
}
