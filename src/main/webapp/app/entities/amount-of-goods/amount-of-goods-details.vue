<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="amountOfGoods">
                <h2 class="jh-entity-heading"><span>AmountOfGoods</span> {{amountOfGoods.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Amount</span>
                    </dt>
                    <dd>
                        <span>{{amountOfGoods.amount}}</span>
                    </dd>
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{amountOfGoods.name}}</span>
                    </dd>
                    <dt>
                        <span>Goods</span>
                    </dt>
                    <dd>
                        <div v-if="amountOfGoods.goods">
                            <router-link :to="{name: 'GoodsView', params: {goodsId: amountOfGoods.goods.id}}">{{amountOfGoods.goods.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="amountOfGoods.id" :to="{name: 'AmountOfGoodsEdit', params: {amountOfGoodsId: amountOfGoods.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./amount-of-goods-details.component.ts">
</script>
