<template>
    <div class="vdatetime">
        <datetime v-model="date" input-id="startDate">
            <label for="startDate" slot="before">Field Label</label>
            <span class="description" slot="after">The field description</span>
            <template slot="button-cancel">
                <fa :icon="['far', 'times']"></fa>
                Cancel
            </template>
            <template slot="button-confirm">
                <fa :icon="['fas', 'check-circle']"></fa>
                Confirm
            </template>
        </datetime>
    </div>
</template>