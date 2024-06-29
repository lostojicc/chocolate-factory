<template>
    <div class="container-fluid contact py-4 wow bounceInUp" data-wow-delay="0.1s">
        <div class="col-12">
                            <div class="text-center">
                                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Your Orders</small>
                            </div>
                        </div>
            <div class="container">
                <div class="row g-1">
                    <div class="col-12 border border-primary bg-light py-3 px-3">
                        <div class="row g-3 d-flex justify-content-around align-items-center">
                            <div v-if="userRole === 'Customer'" class="col-4 d-flex align-items-center justify-content-center">
                                <label class="text-dark mx-4">
                                    Factory:
                                </label>
                                <input type="text" class="form-control border-primary p-2 textWidth mx-2" placeholder="Enter Factory"/>
                            </div>
                            <div class="col-4 d-flex align-items-center justify-content-center">
                                <label class="text-dark mx-4">
                                    Price:
                                </label>
                                <div class="d-flex justify-content-start align-items-center">
                                    <input type="number" class="form-control border-primary p-2 numberWidth mx-2" placeholder="Price"/>
                                    <label class="text-dark">
                                        -
                                    </label>
                                    <input type="number" class="form-control border-primary p-2 numberWidth mx-2" placeholder="Price"/>
                                </div>
                            </div>
                            <div class="col-4 d-flex align-items-center justify-content-center">
                                <label class="text-dark mx-4">
                                    Date:
                                </label>
                                <div class="d-flex justify-content-start align-items-center">
                                    <input type="date" class="form-control border-primary p-2 numberWidth mx-2" placeholder="Price"/>
                                    <label class="text-dark">
                                        -
                                    </label>
                                    <input type="date" class="form-control border-primary p-2 numberWidth mx-2" placeholder="Price"/>
                                </div>
                            </div>
                            <div class="d-flex align-items-center justify-content-center">
                                <button type="submit"  class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill" v-on:click="ClearSearch()">
                                    <i class="fas fa-broom fa-lg px-2"></i>
                                    Clear
                                </button>
                                <button type="submit"  class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill mx-5" v-on:click="Search()">
                                    <i class="fas fa-search fa-lg px-2"></i>
                                    Search
                                </button>      
                            </div>
                        </div>
                    </div>

                    <div class="row d-flex justify-content-center px-3 py-3">
                        <div class="d-flex justify-content-around align-items-center">
                            <div v-if="userRole==='Customer'" class="col-4 d-flex justify-content-center align-items-center">
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-down"></i>
                                </a>
                                <label class="text-dark border-bottom border-primary">
                                    Factory Name
                                </label>
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-up"></i>
                                </a>
                            </div>

                            <div class="col-4 d-flex justify-content-center align-items-center mx-2">
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-down"></i>
                                </a>
                                <label class="text-dark border-bottom border-primary">
                                    Price
                                </label>
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-up"></i>
                                </a>
                            </div>

                            <div class="col-4 d-flex justify-content-center align-items-center">
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-down"></i>
                                </a>
                                <label class="text-dark border-bottom border-primary">
                                    Date
                                </label>
                                <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="UpdateUsername()">
                                    <i class="fas fa-sort-up"></i>
                                </a>
                            </div>

                        </div>
                    </div>
                    <div class="col-12 text-center">
                        <div class="tab-class text-center my-4">
                            <div class="tab-content">
                                <div id="tab-6" class="tab-pane fade show p-0 active">
                                    <div class="row g-5">
                                        <OrderCard v-for="order in orders" :order="order" @loadEvent="load()"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>
import OrderCard from './OrderCard.vue';
import axios  from 'axios';
import {ref, onMounted} from 'vue';
import { useRouter } from 'vue-router';

const userRole = localStorage.getItem('role') || '';
const username = localStorage.getItem('username') || '';
const router = useRouter();

const orders = ref([])

onMounted(async () => {
    load()
});

function load(){
    if(userRole === 'Customer'){
        loadCustomer()
    }
    if(userRole === 'Manager'){
        loadManager()
    }
}

function loadManager(){
    axios.get(`http://localhost:8080/WebShopAppREST/rest/order/getForManager/${username}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
            orders.value = response.data
        }
    }).catch(error => {
        console.error(error.response.data + " | Error status: " + error.response.status);
        alert('Error loading orders!')
        router.push('/')
    });
}

function loadCustomer(){
    axios.get(`http://localhost:8080/WebShopAppREST/rest/order/getForUser/${username}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
            orders.value = response.data
        }
    }).catch(error => {
        console.error(error.response.data + " | Error status: " + error.response.status);
        alert('Error loading orders!')
        router.push('/')
    });
}


</script>

<style scoped>
    .red-border{
        border: 1px solid red;
    }
    .red-labels{
        font-size: medium;
        color: red;
    }
    .invisible{
        visibility: hidden;
    }
    .textWidth{
        width: 290px;
    }
    .numberWidth{
        width: 135px;
    }
</style>
