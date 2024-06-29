<template>
    <HomeHeader/>
    <div class="container-fluid py-3">
        <div v-if="loading" id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-grow text-primary" role="status"></div>
        </div>
        <div v-else>
            <FactoryCard v-for="factory in factories" :key="factory.id" :factory="factory"/>
        </div>
    </div>
</template>

<script setup>
    import FactoryCard from '../components/FactoryCard.vue';
    import AddFactory from '../components/AddFactory.vue';
    import HomeHeader from '../components/HomeHeader.vue';
    import {ref, onMounted} from 'vue';
    import axios from 'axios';

    const factories = ref([]);
    const loading = ref(true);
    const userRole = ref(localStorage.getItem('role') || '');
    const addFormOpen = ref(false);

    onMounted(async () => {
        await loadFactories();
        loading.value = false;
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