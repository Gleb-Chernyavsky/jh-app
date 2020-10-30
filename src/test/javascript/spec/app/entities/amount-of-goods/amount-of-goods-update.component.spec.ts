/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AmountOfGoodsUpdateComponent from '@/entities/amount-of-goods/amount-of-goods-update.vue';
import AmountOfGoodsClass from '@/entities/amount-of-goods/amount-of-goods-update.component';
import AmountOfGoodsService from '@/entities/amount-of-goods/amount-of-goods.service';

import GoodsService from '@/entities/goods/goods.service';

import DealService from '@/entities/deal/deal.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AmountOfGoods Management Update Component', () => {
    let wrapper: Wrapper<AmountOfGoodsClass>;
    let comp: AmountOfGoodsClass;
    let amountOfGoodsServiceStub: SinonStubbedInstance<AmountOfGoodsService>;

    beforeEach(() => {
      amountOfGoodsServiceStub = sinon.createStubInstance<AmountOfGoodsService>(AmountOfGoodsService);

      wrapper = shallowMount<AmountOfGoodsClass>(AmountOfGoodsUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          amountOfGoodsService: () => amountOfGoodsServiceStub,

          goodsService: () => new GoodsService(),

          dealService: () => new DealService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.amountOfGoods = entity;
        amountOfGoodsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(amountOfGoodsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.amountOfGoods = entity;
        amountOfGoodsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(amountOfGoodsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
