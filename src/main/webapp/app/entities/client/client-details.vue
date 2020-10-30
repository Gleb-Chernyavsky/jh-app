<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="client">
                <h2 class="jh-entity-heading"><span>Client</span> {{client.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{client.name}}</span>
                    </dd>
                    <dt>
                        <span>Age</span>
                    </dt>
                    <dd>
                        <span>{{client.age}}</span>
                    </dd>
                    <dt>
                        <span>Phone Number</span>
                    </dt>
                    <dd>
                        <span>{{client.phoneNumber}}</span>
                    </dd>
                    <dt>
                        <span>Email</span>
                    </dt>
                    <dd>
                        <span>{{client.email}}</span>
                    </dd>
                    <dt>
                        <span>Photo</span>
                    </dt>
                    <dd>
                        <div v-if="client.photo">
                            <a v-on:click="openFile(client.photoContentType, client.photo)">
                                <img v-bind:src="'data:' + client.photoContentType + ';base64,' + client.photo" style="max-width: 100%;" alt="client image"/>
                            </a>
                            {{client.photoContentType}}, {{byteSize(client.photo)}}
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="client.id" :to="{name: 'ClientEdit', params: {clientId: client.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./client-details.component.ts">
</script>
