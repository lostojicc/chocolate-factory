<template>
    <div class="container-fluid contact pb-5 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-12">
                        <div class="border border-primary bg-light py-5 px-4">
                            <div class="row g-3 form">
                                <div class="col-1">
                                </div>
                                <div class="col-3">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Chocholate Name" v-model="chocolate.name"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideNameValidation}" >{{requiredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-2">
                                    <input type="number" class="form-control border-primary p-2" placeholder="Enter Price here"  min="1" max="150000" step="0.01" v-model="chocolate.price">
                                    <label class="red-labels" v-bind:class="{invisible : hidePriceValidation}" >{{requiredMessage}}</label>
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-2">
                                    <input type="number" class="form-control border-primary p-2" placeholder="Enter Weight here"  min="1" max="150000" step="0.01" v-model="chocolate.grams">
                                    <label class="red-labels" v-bind:class="{invisible : hideWeightValidation}" >{{requiredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-1">
                                </div>
                    
                                <div class="col-3 h-500">
                                    <input type="text" class="form-control border-primary p-2 height" placeholder="Enter Description here" v-model="chocolate.description"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideDescValidation}" >{{requiredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-2 d-flex flex-column height">
                                    <label for="chocolateType">Select chocolate type</label>
                                    <select id="chocolateType" class="form-select border-primary p-2" aria-label="Chocolate type" v-model="chocolate.type">
                                        <option value="Black" selected>Black</option>
                                        <option value="Milky">Milky</option>
                                        <option value="White">White</option>
                                    </select>
                                    <input type="file" class="form-control border-primary p-2 mt-auto" @change="handleFileUpload" accept="image/png"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideImageValidation}" >{{imageMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-2 d-flex flex-column height">
                                    <label for="chocolateType">Select chocolate type</label>
                                    <select id="chocolateType" class="form-select border-primary p-2" aria-label="Chocolate kind" v-model="chocolate.kind">
                                        <option value="Classic" selected>Classic</option>
                                        <option value="Cooking">Cooking</option>
                                        <option value="Drinking">Drinking</option>
                                    </select>
                                    <button type="submit" class="btn btn-primary px-3 py-3 rounded-pill p-2 mt-auto" v-on:click="SubmitButton">{{buttonLabel}}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>
import axios  from 'axios';
import {ref} from 'vue';
import { defineEmits, defineProps , onMounted} from 'vue';

const emit = defineEmits(['addEvent']);

const chocolate = ref({
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
});

const buttonLabel = ref('Add button')

const props = defineProps({
        editInfo: {
            type: JSON,
        },
        factory:{
            type: Object,
            required: true
        }
    });

const selectedFile = ref(null)

const imageMessage = ref('* Please select an pdf image')
const hideImageValidation = ref(true)

const requiredMessage = ref('* Field required')
const hideNameValidation = ref(true)
const hidePriceValidation = ref(true)
const hideWeightValidation = ref(true)
const hideDescValidation = ref(true)

const canExecute = ref(true)


onMounted(() => {
        if(props.editInfo.editMode){
            buttonLabel.value = 'Edit chocolate';
            copyFields();
        }
        else{
            buttonLabel.value = 'Add chocolate';
        }
    });

function copyFields(){
    console.log(props.editInfo.selectedChocolate.name);
    chocolate.value.name = props.editInfo.selectedChocolate.name
    chocolate.value.price = props.editInfo.selectedChocolate.price
    chocolate.value.kind = props.editInfo.selectedChocolate.kind
    chocolate.value.factoryId = props.editInfo.selectedChocolate.factoryId
    chocolate.value.type = props.editInfo.selectedChocolate.type
    chocolate.value.grams = props.editInfo.selectedChocolate.grams
    chocolate.value.description = props.editInfo.selectedChocolate.description
    chocolate.value.isAvailable = props.editInfo.selectedChocolate.isAvailable
    chocolate.value.quantity = props.editInfo.selectedChocolate.quantity
    chocolate.value.imagePath = props.editInfo.selectedChocolate.imagePath
    chocolate.value.id = props.editInfo.selectedChocolate.id
}


function handleFileUpload(event){
    selectedFile.value = event.target.files[0];
}

function SubmitButton(event){
    event.preventDefault();
    canExecute.value = true;
    
    if(chocolate.value.name === ''){
        hideNameValidation.value = false;
        canExecute.value = false;
    }
    else{
        hideNameValidation.value = true;
    }

    if(chocolate.value.price === null || chocolate.value.price === ''){
        hidePriceValidation.value = false;
        canExecute.value = false;
    }
    else{
        hidePriceValidation.value = true;
    }

    if(chocolate.value.grams === null || chocolate.value.grams === ''){
        hideWeightValidation.value = false;
        canExecute.value = false;
    }
    else{
        hideWeightValidation.value = true;
    }

    if(chocolate.value.description === ''){
        hideDescValidation.value = false;
        canExecute.value = false;
    }
    else{
        hideDescValidation.value = true;
    }

    if(props.editInfo.editMode){
        editChocolate();
        return;
    }

    if(!CheckFile()){
        hideImageValidation.value = false;
        canExecute.value = false;
    }
    else{
        hideImageValidation.value = true;
    }

    if(!canExecute.value){
        return;
    }

    const formData = new FormData();
    formData.append('file', selectedFile.value);
    axios.post('http://localhost:8080/WebShopAppREST/rest/file/image', formData, {
                    headers: {
                            'Content-Type': 'multipart/form-data'
                }
                }).then( response => {
                    if (response.status === 200) {
                    chocolate.value.imagePath = response.data;
                    sendChocolate();
                    }
                }).catch(error => {
                        if (error.response.status === 400) {
                            console.error("Bad Request:", error.response.data)
                        }else{
                            alert('Image not saved')
                        }
                    })
}

function CheckFile(){
    if(selectedFile.value === null){
        return false;
    }
    if (!selectedFile.value.type.startsWith('image/png')) {
        return false;
    }
    return true;
}

function sendChocolate(){
    chocolate.value.factoryId = props.factory.id;
    axios.post('http://localhost:8080/WebShopAppREST/rest/chocholate/add', chocolate.value).then( response => {
                if (response.status === 200) {
                    chocolate.value = emptyChocolate.value;
                    console.log(chocolate.value)
                    emit('addEvent', 'Add chocolate success');
                }
        }).catch(error => {
            console.error('Failed to add chocolate: ',error.response.status);
        });
}

function editChocolate(){
    console.log(chocolate.value)
    if(!canExecute.value){
        return;
    }

    if(CheckFile()){
        const formData = new FormData();
        formData.append('file', selectedFile.value);
        axios.post('http://localhost:8080/WebShopAppREST/rest/file/image', formData, {
                    headers: {
                            'Content-Type': 'multipart/form-data'
                }
                }).then( response => {
                    if (response.status === 200) {
                    chocolate.value.imagePath = response.data;
                    updateChocholate();
                    }
                }).catch(error => {
                        if (error.response.status === 400) {
                            console.error("Bad Request:", error.response.data)
                        }else{
                            alert('Image not saved')
                        }
                    })
    }
    else{
        updateChocholate();
    }

}

function updateChocholate(){
    axios.post('http://localhost:8080/WebShopAppREST/rest/chocholate/update', chocolate.value).then( response => {
            chocolate.value = emptyChocolate.value;
            emit('addEvent', 'Add chocolate success');
        }).catch(error => {
            console.error('Failed to update chocolate: ',error.response.status);
        });
}



</script>

<style scoped>
    .height{
        height: 150px;
    }
    .stackpanel{
        display: flex;
        flex-direction: column;
    }
    .red-border{
        border: 1px solid red;
    }
    .red-labels{
        font-size: small;
        color: red;
    }
    .invisible{
        visibility: collapse;
    }
</style>
