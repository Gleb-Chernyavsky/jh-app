import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDeal } from '@/shared/model/deal.model';
import DealService from './deal.service';

@Component
export default class DealDetails extends Vue {
  @Inject('dealService') private dealService: () => DealService;
  public deal: IDeal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dealId) {
        vm.retrieveDeal(to.params.dealId);
      }
    });
  }

  public retrieveDeal(dealId) {
    this.dealService()
      .find(dealId)
      .then(res => {
        this.deal = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
