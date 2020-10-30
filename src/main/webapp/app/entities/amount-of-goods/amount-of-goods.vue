<template>
    <div>
        <h2 id="page-heading">
            <span id="amount-of-goods-heading">Amount Of Goods</span>
            <router-link :to="{name: 'AmountOfGoodsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-amount-of-goods">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Amount Of Goods
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && amountOfGoods && amountOfGoods.length === 0">
            <span>No amountOfGoods found</span>
        </div>
        <div class="table-responsive" v-if="amountOfGoods && amountOfGoods.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('amount')"><span>Amount</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amount'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('goods.name')"><span>Goods</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goods.name'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="amountOfGoods in amountOfGoods"
                    :key="amountOfGoods.id">
                    <td>
                        <router-link :to="{name: 'AmountOfGoodsView', params: {amountOfGoodsId: amountOfGoods.id}}">{{amountOfGoods.id}}</router-link>
                    </td>
                    <td>{{amountOfGoods.amount}}</td>
                    <td>{{amountOfGoods.name}}</td>
                    <td>
                        <div v-if="amountOfGoods.goods">
                            <router-link :to="{name: 'GoodsView', params: {goodsId: amountOfGoods.goods.id}}">{{amountOfGoods.goods.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AmountOfGoodsView', params: {amountOfGoodsId: amountOfGoods.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'AmountOfGoodsEdit', params: {amountOfGoodsId: amountOfGoods.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(amountOfGoods)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
                <infinite-loading
                    ref="infiniteLoading"
                    v-if="totalItems > itemsPerPage"
                    :identifier="infiniteId"
                    slot="append"
                    @infinite="loadMore"
                    force-use-infinite-wrapper=".el-table__body-wrapper"
                    :distance='20'>
                </infinite-loading>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="jhApp.amountOfGoods.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-amountOfGoods-heading">Are you sure you want to delete this Amount Of Goods?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-amountOfGoods" v-on:click="removeAmountOfGoods()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./amount-of-goods.component.ts">
</script>
