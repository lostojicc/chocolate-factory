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
                    <h4 class="text-primary"><span class="text-dark">Total price: </span>${{ chocolate.totalPrice.toFixed(2) }}</h4>
                </div>
            </div>    
            <div class="row p-2 align-items-center">
            <div class="col d-flex align-items-center">
                <h4 class="text-primary m-2">Quantity:</h4>
                <input type="number" class="form-control p-2 w-25" placeholder="Quantity" min="1" max="10000" step="1" v-model="quantity"
                v-bind:class="{ redBorder: !isInputValid, 'border-primary': isInputValid }" @input="onInputChange"/>
                <h4 class="text-primary m-2"> / {{ chocolate.maxQuantity }}</h4>
            </div>
            <div class="col-auto d-flex justify-content-end">
                <a class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="editClick"><i class="fas fa-pencil-alt"></i></a>
                <a class="btn btn-primary btn-sm-square rounded-circle" @click="deleteChocolate"><i class="fas fa-trash-alt"></i></a>
            </div>
        </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps, defineEmits, ref, onMounted } from 'vue';
    import axios from 'axios';

    const userRole = localStorage.getItem('role') || '';
    const emit = defineEmits(['loadEvent']);

    const props = defineProps({
        chocolate: {
            type: Object,
            required: true
        }
    });

    const quantity = ref(0)
    const isInputValid = ref(true)

    onMounted(() => {
        quantity.value = props.chocolate.quantity;
    }
    );

    function editClick(){
        if(!isInputValid.value)
            return;

        if(quantity.value === props.chocolate.quantity)
            return;

        props.chocolate.quantity = quantity.value;

        axios.post('http://localhost:8080/WebShopAppREST/rest/shopping-cart/updateChocholate', props.chocolate,
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}` // Include the Authorization header
            }
        }
        ).then( response => {
                if (response.status === 200) {
                    console.log('Update success');
                    emit('loadEvent', props.chocolate.id); 
                }
        }).catch(error => {
            console.error('Failed to update chocolate quantity: ',error.response.status);
        });
    }

    function deleteChocolate(){
        axios.delete(`http://localhost:8080/WebShopAppREST/rest/shopping-cart/deleteChocholate/${props.chocolate.id}`,
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}` // Include the Authorization header
            }
        }
        ).then( response => {
                if (response.status === 200) {
                    console.log('Delete success');
                    emit('loadEvent', props.chocolate.id); 
                }
        }).catch(error => {
            console.error('Failed to delete chocolate: ',error.response.status);
        });        
    }

    function onInputChange(){
        if(quantity.value <= 0 || quantity.value > props.chocolate.maxQuantity){
            isInputValid.value = false;
            return;
        }

        isInputValid.value = true;
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