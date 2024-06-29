<template>
    <div class="col-lg-6 wow bounceInUp" data-wow-delay="0.1s">
      <div class="border border-primary rounded">
        <div class="col-12 d-flex justify-content-around align-items-center border-bottom border-primary my-2">
          <h4 class="text-primary">#{{ order.identification }}</h4>
          <h4 class="text-primary">{{ order.state }}</h4>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="row">
                <div class="col-md-6">
                    <div class="d-flex flex-column align-items-left justify-content-around">
                        <h5 class="text-primary">Username: <span class="text-dark mx-3">{{ order.username }}</span></h5>
                        <h5 class="text-primary">Factory: <span class="text-dark mx-2">{{ order.factoryName }}</span></h5>
                        <p><strong>{{ formatDate(order.dateTime) }}</strong></p>
                    </div>
                </div>
              <div class="col-md-6 d-flex-column align-items-top justify-content-start">
                <p style="font-size: 1.5em;" class="text-dark"><strong>Total:</strong> ${{ order.price }}</p>
                <button type="submit" class="btn btn-primary btn-md mb-3 rounded-pill" v-on:click="ToggleViewButton()"><i class="fas fa-eye mx-1"></i>Items</button>
              </div>
            </div>

            <div v-if="showItems === true" class="col-12 border-primary" style="border-style: dashed;border-width: 2px;border-left: none;border-right: none;border-bottom:none;">
                <div class="row">
                    <div v-for="(chocolate, index) in chocholates" :key="index" class="col-md-12 mb-2">
                        <div class="d-flex justify-content-around align-items-center">
                            <div>
                                <p><strong>{{ index + 1 }}. {{ chocolate.name }}: {{ chocolate.quantity }} x {{ chocolate.price}}</strong></p>
                            </div>

                            <div>
                                <p><strong>Price: {{ chocolate.totalPrice.toFixed(2) }}$</strong></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="col-12 py-3 d-flex justify-content-around border-primary" style="border-style: dashed;border-width: 2px;border-left: none;border-right: none;border-bottom:none;">
                <button v-if="userRole ==='Manager' && order.state==='Processing'" type="submit" class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill" 
                v-on:click="RejectClick()">
                    <i class="fas fa-times fa-lg px-2"></i>
                    Reject
                </button>
                <button v-if="userRole ==='Customer' && order.state==='Processing'" type="submit" class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill" 
                v-on:click="CancelClick()">
                    <i class="fas fa-times fa-lg px-2"></i>
                    Cancel
                </button>
                <button v-if="userRole ==='Manager' && order.state==='Processing'" type="submit" class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill" 
                v-on:click="AcceptClick()">
                    <i class="fas fa-check fa-lg px-2"></i>
                    Accept
                </button>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>  

<script setup>
    import { defineProps, defineEmits, ref, onMounted } from 'vue';
    import axios from 'axios';

    const userRole = localStorage.getItem('role') || '';
    const username = localStorage.getItem('username') || '';
    const emit = defineEmits(['loadEvent']);
    //emit('loadEvent', props.chocolate.id); 

    const props = defineProps({
        order: {
            type: Object,
            required: true
        }
    });

    const chocholates = ref([])
    const showItems = ref(false)

    onMounted(async () => {
        await loadChocholates();
    });

    async function loadChocholates(){
        try {
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/order/getChocholatesForOrder', props.order, 
            {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            chocholates.value = response.data;
        } catch (error) {
            console.error('Error loading chocholates:', error);
        }
    }

    function formatDate(dateTime) {
        const cleanedDateTime = dateTime.replace('[UTC]', '');
        let date = new Date(cleanedDateTime);
        if (isNaN(date.getTime())) {
            return 'Invalid Date';
        }
        let day = String(date.getDate()).padStart(2, '0');
        let month = String(date.getMonth() + 1).padStart(2, '0');
        let year = date.getFullYear();
        let hours = String(date.getHours()).padStart(2, '0');
        let minutes = String(date.getMinutes()).padStart(2, '0');
        let seconds = String(date.getSeconds()).padStart(2, '0');
        return `${day}-${month}-${year} ${hours}:${minutes}:${seconds}`;
    }

    function ToggleViewButton(){
        if(showItems.value){
            showItems.value = false
        }
        else{
            showItems.value = true
        }
    }

    async function CancelClick(){
        try {
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/order/cancelOrder', props.order, 
            {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            emit('loadEvent', null);
        } catch (error) {
            console.error('Error loading chocholates:', error);
        }
    }

    async function RejectClick(){
        try {
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/order/rejectOrder', props.order, 
            {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            emit('loadEvent', null);
        } catch (error) {
            console.error('Error loading chocholates:', error);
        }
        
    }

    async function AcceptClick(){
        try {
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/order/acceptOrder', props.order, 
            {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            emit('loadEvent', null);
        } catch (error) {
            console.error('Error loading chocholates:', error);
        }
        
    }

</script>

<style scoped>
.redBorder{
    border: red, 1.5px, solid;
}
</style>