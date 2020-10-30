<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhApp.amountOfGoods.home.createOrEditLabel">Create or edit a AmountOfGoods</h2>
                <div>
                    <div class="form-group" v-if="amountOfGoods.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="amountOfGoods.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="amount-of-goods-amount">Amount</label>
                        <input type="number" class="form-control" name="amount" id="amount-of-goods-amount"
                            :class="{'valid': !$v.amountOfGoods.amount.$invalid, 'invalid': $v.amountOfGoods.amount.$invalid }" v-model.number="$v.amountOfGoods.amount.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="amount-of-goods-name">Name</label>
                        <input type="text" class="form-control" name="name" id="amount-of-goods-name"
                            :class="{'valid': !$v.amountOfGoods.name.$invalid, 'invalid': $v.amountOfGoods.name.$invalid }" v-model="$v.amountOfGoods.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="amount-of-goods-goods">Goods</label>
                        <select class="form-control" id="amount-of-goods-goods" name="goods" v-model="amountOfGoods.goods">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="amountOfGoods.goods && goodsOption.id === amountOfGoods.goods.id ? amountOfGoods.goods : goodsOption" v-for="goodsOption in goods" :key="goodsOption.id">{{goodsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.amountOfGoods.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./amount-of-goods-update.component.ts">
</script>
