/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DealDetailComponent from '@/entities/deal/deal-details.vue';
import DealClass from '@/entities/deal/deal-details.component';
import DealService from '@/entities/deal/deal.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Deal Management Detail Component', () => {
    let wrapper: Wrapper<DealClass>;
    let comp: DealClass;
    let dealServiceStub: SinonStubbedInstance<DealService>;

    beforeEach(() => {
      dealServiceStub = sinon.createStubInstance<DealService>(DealService);

      wrapper = shallowMount<DealClass>(DealDetailComponent, { store, localVue, provide: { dealService: () => dealServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDeal = { id: 123 };
        dealServiceStub.find.resolves(foundDeal);

        // WHEN
        comp.retrieveDeal(123);
        await comp.$nextTick();

        // THEN
        expect(comp.deal).toBe(foundDeal);
      });
    });
  });
});
