<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="goods">
                <h2 class="jh-entity-heading"><span>Goods</span> {{goods.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{goods.name}}</span>
                    </dd>
                    <dt>
                        <span>Price</span>
                    </dt>
                    <dd>
                        <span>{{goods.price}}</span>
                    </dd>
                    <dt>
                        <span>Photo</span>
                    </dt>
                    <dd>
                        <div v-if="goods.photo">
                            <a v-on:click="openFile(goods.photoContentType, goods.photo)">
                                <img v-bind:src="'data:' + goods.photoContentType + ';base64,' + goods.photo" style="max-width: 100%;" alt="goods image"/>
                            </a>
                            {{goods.photoContentType}}, {{byteSize(goods.photo)}}
                        </div>
                    </dd>
                    <dt>
                        <span>Category</span>
                    </dt>
                    <dd>
                        <span v-for="(category, i) in goods.categories" :key="category.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'CategoryView', params: {categoryId: category.id}}">{{category.category}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="goods.id" :to="{name: 'GoodsEdit', params: {goodsId: goods.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./goods-details.component.ts">
</script>
