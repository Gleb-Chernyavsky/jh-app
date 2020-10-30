import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Client = () => import('@/entities/client/client.vue');
// prettier-ignore
const ClientUpdate = () => import('@/entities/client/client-update.vue');
// prettier-ignore
const ClientDetails = () => import('@/entities/client/client-details.vue');
// prettier-ignore
const Goods = () => import('@/entities/goods/goods.vue');
// prettier-ignore
const GoodsUpdate = () => import('@/entities/goods/goods-update.vue');
// prettier-ignore
const GoodsDetails = () => import('@/entities/goods/goods-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const AmountOfGoods = () => import('@/entities/amount-of-goods/amount-of-goods.vue');
// prettier-ignore
const AmountOfGoodsUpdate = () => import('@/entities/amount-of-goods/amount-of-goods-update.vue');
// prettier-ignore
const AmountOfGoodsDetails = () => import('@/entities/amount-of-goods/amount-of-goods-details.vue');
// prettier-ignore
const Deal = () => import('@/entities/deal/deal.vue');
// prettier-ignore
const DealUpdate = () => import('@/entities/deal/deal-update.vue');
// prettier-ignore
const DealDetails = () => import('@/entities/deal/deal-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/client',
    name: 'Client',
    component: Client,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/client/new',
    name: 'ClientCreate',
    component: ClientUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/client/:clientId/edit',
    name: 'ClientEdit',
    component: ClientUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/client/:clientId/view',
    name: 'ClientView',
    component: ClientDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/goods',
    name: 'Goods',
    component: Goods,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/goods/new',
    name: 'GoodsCreate',
    component: GoodsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/goods/:goodsId/edit',
    name: 'GoodsEdit',
    component: GoodsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/goods/:goodsId/view',
    name: 'GoodsView',
    component: GoodsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category',
    name: 'Category',
    component: Category,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/new',
    name: 'CategoryCreate',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/edit',
    name: 'CategoryEdit',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/view',
    name: 'CategoryView',
    component: CategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amount-of-goods',
    name: 'AmountOfGoods',
    component: AmountOfGoods,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amount-of-goods/new',
    name: 'AmountOfGoodsCreate',
    component: AmountOfGoodsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amount-of-goods/:amountOfGoodsId/edit',
    name: 'AmountOfGoodsEdit',
    component: AmountOfGoodsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amount-of-goods/:amountOfGoodsId/view',
    name: 'AmountOfGoodsView',
    component: AmountOfGoodsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/deal',
    name: 'Deal',
    component: Deal,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/deal/new',
    name: 'DealCreate',
    component: DealUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/deal/:dealId/edit',
    name: 'DealEdit',
    component: DealUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/deal/:dealId/view',
    name: 'DealView',
    component: DealDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
