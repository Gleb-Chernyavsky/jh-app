/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DealUpdateComponent from '@/entities/deal/deal-update.vue';
import DealClass from '@/entities/deal/deal-update.component';
import DealService from '@/entities/deal/deal.service';

import ClientService from '@/entities/client/client.service';

import AmountOfGoodsService from '@/entities/amount-of-goods/amount-of-goods.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Deal Management Update Component', () => {
    let wrapper: Wrapper<DealClass>;
    let comp: DealClass;
    let dealServiceStub: SinonStubbedInstance<DealService>;

    beforeEach(() => {
      dealServiceStub = sinon.createStubInstance<DealService>(DealService);

      wrapper = shallowMount<DealClass>(DealUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          dealService: () => dealServiceStub,

          clientService: () => new ClientService(),

          amountOfGoodsService: () => new AmountOfGoodsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(format(date, DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.deal = entity;
        dealServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dealServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.deal = entity;
        dealServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dealServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
