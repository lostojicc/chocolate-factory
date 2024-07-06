<template>
    <div class="container-fluid menu py-4">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Your Shopping Cart</small>
            </div>
            <div class="tab-class text-center">
                <div class="tab-content">
                    <div id="tab-6" class="tab-pane fade show p-0 active">
                        <div class="row g-4">
                          <ChocholateItemCard v-for="chocolate in chocolates" :chocolate="chocolate" @loadEvent="handleLoadEvent"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="fixed-bottom bg-light">
        <div class="container text-center">
          <div class="row">
            <div class="col">
              <h3 class="mb-4">Total price: <span v-bind:class="{crossed : customerType.discount !== 0.0}">{{cart.price.toFixed(2)}}</span>
                <span class="text-primary mx-3" v-if="customerType.discount !== 0.0">{{discountedPrice.toFixed(2)}}</span>$</h3>
            </div>
            <div class="w-100">
                
            </div>
            <div class="col">
              <button type="submit" class="btn btn-primary btn-lg mx-5 mb-5 rounded-pill" v-on:click="ClearButton">Clear Cart</button>
              <button type="submit" class="btn btn-primary btn-lg mx-5 mb-5 rounded-pill" v-on:click="CheckoutButton">Checkout</button>
            </div>
          </div>
        </div>
    </div>
</template>

<style scoped>
.crossed {
    text-decoration: line-through;
  }
</style>

<script setup>
import { ref, onMounted } from 'vue';
import ChocholateItemCard from './ChocholateItemCard.vue';
import axios from 'axios';

const userRole = localStorage.getItem('role') || '';
const username = localStorage.getItem('username') || '';

const cart = ref({
    id: 0,
	userId: 0,
	price: 0
})

const discountedPrice = ref(0.0)

const customerType = ref({
    typeName: 'None',
	discount: 0.0
})

const chocolates = ref([])

onMounted(async () => {
    try {
        await loadCart();
        await loadCustomerType();
        await loadChocolates();
    } catch (error) {
        console.error('Error during initialization: ', error);
    }
});

async function loadCart(){
    try {
        const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/getCart/${username}`);
        if (response.status === 200) {
            cart.value = response.data;
        }
    } catch (error) {
        console.error('Failed to find cart: ', error.response?.status);
        throw error; 
    }
}

async function loadCustomerType(){
    try {
        const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/getDiscount/${username}`);
        if (response.status === 200) {
            customerType.value = response.data;
            discountedPrice.value = cart.value.price - cart.value.price * customerType.value.discount;
        }
    } catch (error) {
        console.error('Failed to find customer type: ', error.response?.status);
        throw error; 
    }
}

async function loadChocolates() {
    try {
        const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/getChocholates/${cart.value.id}`,{
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
            }
          });
        if (response.status === 200) {
            chocolates.value = response.data;
        }
    } catch (error) {
        console.error('Failed to load chocolates: ', error.response?.status);
        throw error;
    }
}

  async function handleLoadEvent(data){
      try {
        await loadCart();
        await loadCustomerType();
        await loadChocolates();
      } catch (error) {
        console.error('Error during refreshing: ', error);
    }
  }

async function ClearButton(){
    try {
        const deletionPromises = chocolates.value.map(chocolate => DeleteChocholate(chocolate.id));
        
        await Promise.all(deletionPromises);
        await handleLoadEvent();
    } catch (error) {
        console.error('Error during clearing chocolates: ', error);
    }
}

function DeleteChocholate(id){
    return axios.delete(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/deleteChocholate/${id}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
            console.log('Delete success');
        }
    }).catch(error => {
        console.error('Failed to delete chocolate: ', error.response.status);
    });
}

function CheckoutButton(){
    if(chocolates.value.length === 0){
        alert('Cart is empty, nothing to checkout!');
        return;
    }

    axios.post('http://localhost:8080/WebShopAppREST/rest/shopping-cart/checkout', cart.value,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
            handleLoadEvent()
            console.log('Checkout success');
        }
    }).catch(error => {
        console.error(error.response.data + " : " + error.response.status)
    });
}

</script>