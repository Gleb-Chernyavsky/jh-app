<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhApp.category.home.createOrEditLabel">Create or edit a Category</h2>
                <div>
                    <div class="form-group" v-if="category.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="category.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="category-category">Category</label>
                        <input type="text" class="form-control" name="category" id="category-category"
                            :class="{'valid': !$v.category.category.$invalid, 'invalid': $v.category.category.$invalid }" v-model="$v.category.category.$model"  required/>
                        <div v-if="$v.category.category.$anyDirty && $v.category.category.$invalid">
                            <small class="form-text text-danger" v-if="!$v.category.category.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.category.category.minLength">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.category.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./category-update.component.ts">
</script>
