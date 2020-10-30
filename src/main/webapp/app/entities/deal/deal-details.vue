<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="deal">
                <h2 class="jh-entity-heading"><span>Deal</span> {{deal.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Date</span>
                    </dt>
                    <dd>
                        <span>{{deal.date | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Client</span>
                    </dt>
                    <dd>
                        <div v-if="deal.client">
                            <router-link :to="{name: 'ClientView', params: {clientId: deal.client.id}}">{{deal.client.name}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span>Amount Of Goods</span>
                    </dt>
                    <dd>
                        <span v-for="(amountOfGoods, i) in deal.amountOfGoods" :key="amountOfGoods.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'AmountOfGoodsView', params: {amountOfGoodsId: amountOfGoods.id}}">{{amountOfGoods.name}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="deal.id" :to="{name: 'DealEdit', params: {dealId: deal.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./deal-details.component.ts">
</script>
