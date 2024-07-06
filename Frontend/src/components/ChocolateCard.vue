<template>
    <div class="col-lg-6 wow bounceInUp" data-wow-delay="0.1s">
        <div class="border border-primary rounded">
            <div class="menu-item d-flex align-items-center p-3 border-bottom border-primary">
                <img class="flex-shrink-0 img-fluid rounded-circle image-klasick" :src="chocolate.imagePath" alt="">
                <div class="w-100 d-flex flex-column text-start ps-4">
                    <div class="row justify-content-between border-bottom border-primary pb-2 mb-2">
                        <div class="col-md-8 d-flex flex-column">
                            <h4>{{ chocolate.name }}</h4>
                        <div>
                            ( {{ chocolate.kind }}, {{ chocolate.type }} )
                        </div>
                        </div>
                        
                        <div class="col-md-4">
                            <h4 class="text-primary">${{ chocolate.price }}<span class="text-dark">/ {{ chocolate.grams }}g</span></h4>
                        </div>
                    </div>
                    <p class="mb-0">{{ chocolate.description }}</p>
                </div>
            </div>    
            <div class="row p-3">
                <div v-if="chocolate.quantity != 0" class="col-5 d-flex align-items-center">
                    <h4 class="text-primary m-2">Available:</h4>
                    <h4 class="text-dark m-2">{{ chocolate.quantity }}</h4>
                </div>
                <div v-else class="col-5 d-flex align-items-center">
                    <h4 class="text-dark m-2">Not available</h4>
                </div>
                <div v-if="quantityOpen && userRole==='Worker'" class="col-4 d-flex align-items-center">
                    <input type="number" class="form-control p-2 me-2" placeholder="Quantity"  min="1" max="10000" step="1" v-model="quantity"
                    v-bind:class="{redBorder : !isInputValid},{'border-primary' : isInputValid}"/>
                    <a v-if="userRole === 'Worker' && editable" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="editQuantity"><i class="fas fa-save"></i></a>
                </div>
                <div v-if="chocolate.quantity != 0 && userRole==='Customer'" class="col-4 d-flex align-items-center">
                    <h4 class="text-primary m-2">Buy:</h4>
                    <input type="number" class="form-control p-2" placeholder="Quantity"  min="1" max="10000" step="1" v-model="chocoInstance.quantity"
                    v-bind:class="{redBorder : !isInputValid},{'border-primary' : isInputValid}"/>
                </div>
                <div class="col d-flex align-items-center">
                    <a v-if="chocolate.quantity != 0 && userRole==='Customer'" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="shopClick()"><i class="fas fa-shopping-bag"></i></a>
                    <a v-if="userRole === 'Worker' && editable" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="editQuantityClick"><i class="fas fa-pencil-alt"></i></a>
                    <a v-if="userRole === 'Manager' && editable" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="editClick()"><i class="fas fa-pencil-alt"></i></a>
                    <a v-if="userRole === 'Manager' && editable" class="btn btn-primary btn-sm-square rounded-circle" @click="deleteChocolate()"><i class="fas fa-trash-alt"></i></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps, defineEmits, ref } from 'vue';
    import axios from 'axios';

    const userRole = localStorage.getItem('role') || '';
    const username = localStorage.getItem('username') || '';
    const emit = defineEmits(['editEvent', 'deleteEvent', 'buyEvent', 'editQuantityEvent']);
    const isInputValid = ref(true)

    const props = defineProps({
        chocolate: {
            type: Object,
            required: true
        },
        editable: {
            type: Boolean,
            required: true
        }
    });

    function editQuantityClick(){
        quantityOpen.value = !quantityOpen.value;
        quantity.value = props.chocolate.quantity;
    }

    const quantityOpen = ref(false);
    const quantity = ref(0);

    const chocoInstance = ref({
	    cartId : 0,
        chocholateId : props.chocolate.id,
	    quantity : 1
    })

    function editQuantity(){
        emit('editQuantityEvent', { quantity: quantity.value, chocolateId: props.chocolate.id});
        quantityOpen.value = !quantityOpen.value;
    }

    function editClick(){
        emit('editEvent', props.chocolate);
    }

    function deleteChocolate(){
        emit('deleteEvent', props.chocolate.id); 
    }

    function shopClick(){
        isInputValid.value = true;
        if(chocoInstance.value.quantity <= 0 || chocoInstance.value.quantity > props.chocolate.quantity){
            isInputValid.value = false;
            return;
        }
        
        axios.get(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/getCart/${username}`
        ).then( response => {
                if (response.status === 200) {
                    let cart = response.data
                    chocoInstance.value.cartId = cart.id
                    SendChocolateToCart()
                }
        }).catch(error => {
            console.error('Failed to find cart: ',error.response.status);
        });
    }

    function SendChocolateToCart(){
        axios.post('http://localhost:8080/WebShopAppREST/rest/shopping-cart/addChocholate', chocoInstance.value,
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}` // Include the Authorization header
            }
        }
        ).then( response => {
                if (response.status === 200) {
                    console.log('Added success');
                    emit('buyEvent', props.chocolate.id); 
                }
        }).catch(error => {
            if (error.response.status === 400) {
                alert(error.response.data)
            }else{
                console.error('Failed to add chocolate to cart: ',error.response.status);
            }
        });
    }
</script>

<style scoped>
.image-klasick{
    height: 100px; 
    width: 100px;
    object-fit: cover;
}
.redBorder{
    border: red, 1.5px, solid;
}
</style>