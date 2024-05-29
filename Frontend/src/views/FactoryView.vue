<template>
    <div v-if="loading" id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
      <div class="spinner-grow text-primary" role="status"></div>
    </div>
    <div v-if="factory && factory.location && factory.location.address">
        <FactoryInformation :factory="factory"/>
    <!-- <CommentSection/> -->
        <ChocolateDisplay :factory="factory"/>
    </div>   
</template>

<script setup>
import FactoryInformation from '@/components/FactoryInformation.vue';
import CommentSection from '@/components/CommentSection.vue';
import ChocolateDisplay from '@/components/ChocolateDisplay.vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const factory = ref(null);
const loading = ref(true);
const error = ref(false);
const route = useRoute();

onMounted(async () => {
  await loadFactory();
  loading.value = false;
});

async function loadFactory() {
  const factoryId = route.params.id;

  if (factoryId) {
    try {
      const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/${factoryId}`);
      factory.value = response.data;

      const locationResponse = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/location/${factory.value.locationId}`);
      factory.value.location = locationResponse.data;

      const addressResponse = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/location/address/${factory.value.location.addressId}`);
      factory.value.location.address = addressResponse.data;
    } catch (error) {
      console.error('Error loading factory data:', error);
      error.value = true;
    }
  } else {
    error.value = true;
  }
}
</script>