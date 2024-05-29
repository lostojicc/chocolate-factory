<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-12">
                        <div class="border border-primary bg-light py-5 px-4">
                            <div class="row g-3 form">
                                <div class="col-1">
                                </div>
                                <div class="col-3">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Chocholate Name" v-model="chocolate.name"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideUsernameValidation}" >{{reqiuredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-2">
                                    <input type="number" class="form-control border-primary p-2" placeholder="Enter Price here"  min="1" max="150000" step="0.01" v-model="chocolate.price">
                                    <label class="red-labels" v-bind:class="{invisible : hideNameValidation}" >{{reqiuredMessage}}</label>
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-2">
                                    <input type="number" class="form-control border-primary p-2" placeholder="Enter Weight here"  min="1" max="150000" step="0.01" v-model="chocolate.grams">
                                    <label class="red-labels" v-bind:class="{invisible : hidePasswordValidation}" >{{reqiuredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-1">
                                </div>
                    
                                <div class="col-3 h-500">
                                    <input type="text" class="form-control border-primary p-2 height" placeholder="Enter Description here" v-model="chocolate.description"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideUsernameValidation}" >{{reqiuredMessage}}</label>
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
                                    <input type="file" class="form-control border-primary p-2 mt-auto" @change="handleFileUpload" accept="image/*"/>
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
                                    <button type="submit" class="btn btn-primary px-3 py-3 rounded-pill p-2 mt-auto" v-on:click="SubmitButton">Add Chocholate</button>
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
import {ref, onMounted} from 'vue';
import { useRouter } from 'vue-router';

const chocolate = ref({
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

const selectedFile = ref(null)

const imageMessage = ref('* Please select an image')
const hideImageValidation = ref(true)


function handleFileUpload(event){
    selectedFile.value = event.target.files[0];
}

function SubmitButton(event){
    event.preventDefault();
    
    if(!CheckFile()){
        hideImageValidation.value = false;
        return;
    }
    else{
        hideImageValidation.value = true;
    }


    const formData = new FormData();
    formData.append('file', selectedFile.value);
    axios.post('http://localhost:8080/WebShopAppREST/rest/file/image', formData, {
                    headers: {
                            'Content-Type': 'multipart/form-data'
                }
                }).then( response => {
                    if (response.status === 200) {
                    alert('Image sent succsesfully!')
                    sendChocolate();
                    chocolate.value.imagePath = response.data;
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
    if (!selectedFile.value.type.startsWith('image/') || selectedFile.value === null) {
        return false;
    }
    return true;
}


function sendChocolate(){
    console.log(chocolate.value)
    axios.post('http://localhost:8080/WebShopAppREST/rest/chocholate/add', chocolate.value).then( response => {
                if (response.status === 200) {
                    alert('Chocholate add succsesfull!')
                }
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
