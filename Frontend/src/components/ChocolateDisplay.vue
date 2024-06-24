<template>
    <div class="container-fluid menu py-6">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Our Products</small>
                <h1 class="display-5 mb-5">Best Chocolates in the World
                    <a v-if="userRole === 'Manager'" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="addClick"><i class="fas fa-plus"></i></a>
                </h1>
            </div>
                <AddChocholate v-if="openForm" @addEvent="handleAddEvent" :editInfo="editInfo" :factory="factory"/>
            <div class="tab-class text-center">
            <div class="tab-content">
                <div id="tab-6" class="tab-pane fade show p-0 active">
                    <div class="row g-4">
                        <ChocolateCard v-for="chocolate in chocolates" :chocolate="chocolate" @editEvent="handleEditEvent" @deleteEvent="handleDeleteEvent"/>
                    </div>
                </div>
            </div>
        </div>
        </div>   
    </div>
</template>

<script setup>
    import ChocolateCard from './ChocolateCard.vue';
    import AddChocholate from './AddChocholate.vue';
    import { defineProps } from 'vue';
    import {ref, onMounted } from 'vue';
    import axios from 'axios';

    const userRole = localStorage.getItem('role') || '';

    const props = defineProps({
        factory: {
            type: Object,
            required: true
        }
    });

    const chocolates = ref([]);
    const openForm = ref(false);

    const emptyChocolate = ref({
    id: 0,
    name: '',
    price: null,
	kind: 'Classic',
	factoryId: 1,
	type: 'Black',
	grams: null,
	description: '',
    isAvailable: false,
	quantity: 0,
    imagePath: ''
})

    const editInfo ={
        selectedChocolate : emptyChocolate.value,
        editMode : false
    };

    onMounted(async () => {
        await loadChocolates();
    });

    function handleDeleteConfirmationEvent(chocolateId){
        axios.delete(`http://localhost:8080/WebShopAppREST/rest/chocholate/delete/${chocolateId}`).then(response => {
            console.log("Success: ", response.data);  
            loadChocolates(); 
        }).catch(error => {
            console.error("Bad request: ", error.response.data);
        })
    };

    async function loadChocolates() {
        try {
            const chocolateResponse = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/chocolates/${props.factory.id}`);
            chocolates.value = chocolateResponse.data;
        } catch (error) {
            console.error('Error loading chocolates:', error);
        }
    }

    function addClick(){
        editInfo.editMode = false;

        if(openForm.value){
            openForm.value = false;
        }else{
            openForm.value = true;
        }
    }


    function handleAddEvent(data){
        loadChocolates();
        editInfo.editMode = false;
        openForm.value = false;
    }

    function handleEditEvent(data){
        if(!openForm.value){
            editInfo.selectedChocolate = data;
            editInfo.editMode = true;

            openForm.value = true;
        }
        else{
            openForm.value = false;
        }
    }

    function handleDeleteEvent(data){
        // chocolateForDeletionId = data;
        // emit('deleteeEvent');
        let text;
        if (confirm("Are you sure you want to delete this chocolate?") == true) 
            handleDeleteConfirmationEvent(data);
    }
</script>
