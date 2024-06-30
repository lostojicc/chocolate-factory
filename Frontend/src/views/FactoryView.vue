<template>
    <div v-if="loading" id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
      <div class="spinner-grow text-primary" role="status"></div>
    </div>
    
    <div v-if="factory && factory.location && factory.location.address">
        <FactoryInformation :factory="factory"/>
        <div class="tab-class text-center">
            <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                <li class="nav-item p-2" v-on:click="ChangeTab('Chocolates')">
                    <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                      :class="{ 'bg-primary': showTab === 'Chocolates', 'bg-white' : showTab!== 'Chocolates'}">
                        <span class="text-dark" style="width: 150px;">Chocolates</span>
                    </button>
                </li>
                <li class="nav-item p-2" v-on:click="ChangeTab('Comments')">
                    <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                    :class="{ 'bg-primary': showTab === 'Comments', 'bg-white' : showTab!== 'Comments'}">
                        <span class="text-dark" style="width: 150px;">Comments</span>
                    </button>
                </li>
                <li v-if="userRole === 'Manager' && factoryId === factory.id" class="nav-item p-2" v-on:click="ChangeTab('Workers')">
                    <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                    :class="{ 'bg-primary': showTab === 'Workers', 'bg-white' : showTab!== 'Workers'}">
                        <span class="text-dark" style="width: 150px;">Workers</span>
                    </button>
                </li>
                <li v-if="userRole === 'Manager' && factoryId === factory.id" class="nav-item p-2" v-on:click="ChangeTab('Orders')">
                    <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                    :class="{ 'bg-primary': showTab === 'Orders', 'bg-white' : showTab!== 'Orders'}">
                        <span class="text-dark" style="width: 150px;">Orders</span>
                    </button>
                </li>
            </ul>
            <ChocolateDisplay v-show="showTab === 'Chocolates'" :factory="factory" :editable="editable"/>
            <CommentSection v-show="showTab === 'Comments'" :factory="factory" :editable="editable"/>
            <Orders v-show="showTab === 'Orders'"/>
        </div>
        
    </div>   
</template>

<script setup>
import FactoryInformation from '@/components/FactoryInformation.vue';
import CommentSection from '@/components/CommentSection.vue';
import ChocolateDisplay from '@/components/ChocolateDisplay.vue';
import Orders from '@/components/Orders.vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const factory = ref(null);
const loading = ref(true);
const error = ref(false);
const route = useRoute();
const showTab = ref('Chocolates')

const userRole = ref(localStorage.getItem('role') || '');
const username = ref(localStorage.getItem('username') || '');
const factoryId = ref(0)
const editable = ref(false)
// const modal = ref(false);

onMounted(async () => {
  await loadFactory();
  await loadFactoryForUser();
  loading.value = false;
});

// function handleDeleteEvent(){
//   modal.value = true;
// }

// function deleteCancelled(){
//   modal.value = false;
// }

// function deleteConfirmed(){
//   modal.value = false;
// }

async function loadFactoryForUser(){
        try {
            if(userRole.value === 'Manager' || userRole.value === 'Worker'){
                const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/getFactoryByUsername/${username.value}`);
                factoryId.value = response.data
                console.log(factory.value.id)
                if(factoryId.value === factory.value.id){
                    editable.value = true;
                }
            }
        } catch (error) {
            console.error('Error loading factory:', error);
        } 
    }

function ChangeTab(tabName){
  showTab.value = tabName

}

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