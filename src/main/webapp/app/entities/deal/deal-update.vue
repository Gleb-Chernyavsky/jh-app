<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhApp.deal.home.createOrEditLabel">Create or edit a Deal</h2>
                <div>
                    <div class="form-group" v-if="deal.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="deal.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="deal-date">Date</label>
                        <div class="d-flex">
                            <input id="deal-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.deal.date.$invalid, 'invalid': $v.deal.date.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.deal.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                        <div v-if="$v.deal.date.$anyDirty && $v.deal.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.deal.date.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.deal.date.ZonedDateTimelocal">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="deal-client">Client</label>
                        <select class="form-control" id="deal-client" name="client" v-model="deal.client">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="deal.client && clientOption.id === deal.client.id ? deal.client : clientOption" v-for="clientOption in clients" :key="clientOption.id">{{clientOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="deal-amountOfGoods">Amount Of Goods</label>
                        <select class="form-control" id="deal-amountOfGoods" multiple name="amountOfGoods" v-model="deal.amountOfGoods">
                            <option v-bind:value="getSelected(deal.amountOfGoods, amountOfGoodsOption)" v-for="amountOfGoodsOption in amountOfGoods" :key="amountOfGoodsOption.id">{{amountOfGoodsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.deal.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./deal-update.component.ts">
</script>
