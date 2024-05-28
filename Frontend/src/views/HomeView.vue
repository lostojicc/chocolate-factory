<template>
    <div class="container-fluid py-4">
        <FactoryCard v-for="factory in factories" :factory="factory"/>
    </div>
</template>

<script setup>
    import FactoryCard from '../components/FactoryCard.vue';
    import {ref, onMounted} from 'vue';
    import axios from 'axios';

    const factories = ref([]);

    onMounted(async () => {
        await loadFactories();
    });

    async function loadFactories() {
        try {
            const factoryResponse = await axios.get("http://localhost:8080/WebShopAppREST/rest/factory/");
            factories.value = factoryResponse.data;

            for (let factory of factories.value) {
                const locationResponse = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/location/${factory.locationId}`);
                factory.location = locationResponse.data;

                const addressResponse = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/location/address/${factory.location.addressId}`);
                factory.location.address = addressResponse.data;
            }
        } catch (error) {
            console.error('Error loading factories:', error);
        }
    }
</script>

<style>

</style>