/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AmountOfGoodsDetailComponent from '@/entities/amount-of-goods/amount-of-goods-details.vue';
import AmountOfGoodsClass from '@/entities/amount-of-goods/amount-of-goods-details.component';
import AmountOfGoodsService from '@/entities/amount-of-goods/amount-of-goods.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AmountOfGoods Management Detail Component', () => {
    let wrapper: Wrapper<AmountOfGoodsClass>;
    let comp: AmountOfGoodsClass;
    let amountOfGoodsServiceStub: SinonStubbedInstance<AmountOfGoodsService>;

    beforeEach(() => {
      amountOfGoodsServiceStub = sinon.createStubInstance<AmountOfGoodsService>(AmountOfGoodsService);

      wrapper = shallowMount<AmountOfGoodsClass>(AmountOfGoodsDetailComponent, {
        store,
        localVue,
        provide: { amountOfGoodsService: () => amountOfGoodsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAmountOfGoods = { id: 123 };
        amountOfGoodsServiceStub.find.resolves(foundAmountOfGoods);

        // WHEN
        comp.retrieveAmountOfGoods(123);
        await comp.$nextTick();

        // THEN
        expect(comp.amountOfGoods).toBe(foundAmountOfGoods);
      });
    });
  });
});
