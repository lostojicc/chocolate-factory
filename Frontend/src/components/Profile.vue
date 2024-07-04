<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-start" style="object-fit: cover; opacity: 0.8;" alt="">
                    </div>
                    <div class="col-10">
                        <div class="border-bottom border-top border-primary bg-light py-3 px-4">
                            <div class="text-center">
                                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Your Profile</small>
                            </div>
                            <div class="row text-center mb-1">
                                <div class="m-2">
                                    <div class="border-bottom border-primary d-inline-block"><h4>Personal information:</h4></div>
                                </div>
                            </div>
                            
                            <div class="row g-2 form my-2">
                                <div class="col-1">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-between">
                                    <label class="text-dark" > Name: </label>
                                    <div class="d-flex align-items-center">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Name" v-model="user.name"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="UpdateName()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div class="col-2">
                                    <label class="text-dark">Gender</label>
                                    <select class="form-select border-primary p-2" aria-label="Gender" v-model="user.gender" @change="onGenderChange()">
                                        <option value="Male" selected>Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <label class="text-dark">Date of Birth</label>
                                    <input type="date" class="form-control border-primary p-2" placeholder="Birth Date" v-model="user.dateOfBirth" @change="onDateChange()"/>
                                </div>

                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-between">
                                    <label class="text-dark" > Surname: </label>
                                    <div class="d-flex align-items-center">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Surname" v-model="user.surname"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="UpdateSurname()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div v-if="userRole === 'Customer'" class="col-4 d-flex align-items-center justify-content-start">
                                    <label class="text-dark" > Number of points: 
                                    </label>
                                    <h5 class="text-primary font-weight-bold m-2"> {{customer.points}}</h5>
                                    <h5 v-if="customer.typeName != 'None'" class="text-primary font-weight-bold m-2">({{customer.typeName}})</h5>
                                </div>

                                <div class="col-1">
                                </div>

                            </div>
                            <div class="row text-center mt-5">
                                <div class="m-2">
                                    <div class="border-bottom border-primary d-inline-block"><h4>Profile information:</h4></div>
                                </div>
                            </div>

                            <div class="row text-center mb-4">
                                <span>Data change will redirect you to login</span>
                            </div>

                            <div v-if="isPasswordCorrect === 0 || isPasswordCorrect === 2" class="row form mt-1">
                                <div class="col-3"></div>
                                <div class="col-6 d-flex align-items-center justify-content-center">
                                    <label>Enter password to show information</label>
                                    <input type="password" class="form-control border-primary p-2" placeholder="Enter Your Password" v-model="password">
                                </div>
                                <div class="col-3"></div>

                                <div class="col-12 text-center my-4">
                                    <button type="submit" class="btn btn-primary px-4 py-2 rounded-pill" v-on:click="CheckPassword()">Submit</button>
                                </div>
                            </div>
                            
                            <div v-if="isPasswordCorrect === 1" class="row form my-4">
                                <div class="col-1">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <label class="text-dark" >Username: </label>
                                    <div class="d-flex align-items-center mx-1">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Username" v-model="user.username"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="UpdateUsername()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <label class="text-dark" >Password: </label>
                                    <div class="d-flex align-items-center mx-1">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Change Your Password" v-model="user.password"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="UpdatePassword()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-1">
                                </div>
                            </div>

                            <div v-if="isPasswordCorrect === 2" class="row form my-4">
                                <div class="col-12 text-center">
                                    <label class="red-labels"> Password incorrect !</label>
                                </div>  
                            </div>

                        </div>
                    </div>
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-end" style="object-fit: cover; opacity: 0.8;" alt="">
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>
import axios  from 'axios';
import {ref, onMounted} from 'vue';
import { useRouter } from 'vue-router';

const userRole = localStorage.getItem('role') || '';
const username = localStorage.getItem('username') || '';
const password = ref('')
const router = useRouter();

const user = ref({
    id: '',
    username: '',
    password: '',
    name: '',
    surname: '',
    gender: 'Male',
    dateOfBirth: '',
    role: userRole.value,
})

const customer = ref({
    points: 0,
    typeName: ''
})

const isPasswordCorrect = ref(0)

onMounted(async () => {
    load()
});

function load(){
    if(userRole === 'Customer'){
        loadCustomer()
    }
}

function loadCustomer(){
    axios.get(`http://localhost:8080/WebShopAppREST/rest/user/get/${username}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
            user.value = response.data
            customer.value = response.data
        }
    }).catch(error => {
        console.error(error.response.data + " | Error status: " + error.response.status);
        router.push('/')
    });
}

function UpdateName(){
    Update(1)
}

function UpdateSurname(){
    Update(2)
}

function onDateChange(){
    Update(3)
}

function onGenderChange(){
    Update(4)
}

function UpdateUsername(){
    Update(5)  //problem zbog json tokena mora da se menja sto je bas rak
}

function UpdatePassword(){
    Update(6)
}

function CheckPassword(){
    if(password.value === user.value.password){
        isPasswordCorrect.value = 1
    }
    else{
        isPasswordCorrect.value = 2
    }
}

async function Update(data){
    await axios.post(`http://localhost:8080/WebShopAppREST/rest/user/updateName/${data}`, user.value,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then(response => {
        if (response.status === 200) {
        }
    }).catch(error => {
        console.error(error.response.data + " | Error status: " + error.response.status);
    });
    if(data == 5 || data == 6){
        signOut()
    }
    else{
        load()
    }
}

function signOut(){
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    location.reload();
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
</style>
