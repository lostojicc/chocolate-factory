<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-start" style="object-fit: cover; opacity: 0.8;" alt="">
                    </div>
                    <div class="col-10">
                        <div class="border-bottom border-top border-primary bg-light py-5 px-4">
                            <div class="text-center">
                                <small v-if="props.factory == null" class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Welcome</small>
                                <h1 v-if="props.factory == null" class="display-5 mb-5">Sign up</h1>
                                <h1 v-if="props.factory != null" class="display-5 mb-5">Register new manager</h1>
                            </div>
                            <div class="row g-4 form">
                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Username" v-model="user.username"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideUsernameValidation}" >{{usernameMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Name" v-model="user.name"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideNameValidation}" >{{requiredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>


                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="password" class="form-control border-primary p-2" placeholder="Enter Your Password" v-model="user.password"/>
                                    <label class="red-labels" v-bind:class="{invisible : hidePasswordValidation}" >{{passwordMessage}}</label>
                                </div>
                                <div class="col-2">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Surname" v-model="user.surname"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideSurnameValidation}" >{{requiredMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <label></label>
                                    <input type="password" class="form-control border-primary p-2" placeholder="Repeat Your Password" v-model="repeatedPassword"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideRepeatValidation}" >{{repeatMessage}}</label>
                                </div>
                                <div class="col-2">
                                </div>
                                <div class="col-2">
                                    <label>Select gender</label>
                                    <select class="form-select border-primary p-2" aria-label="Gender" v-model="user.gender">
                                        <option value="Male" selected>Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <label>Enter birth date</label>
                                    <input type="date" class="form-control border-primary p-2" placeholder="Birth Date" v-model="user.dateOfBirth"/>
                                    <label class="red-labels" v-bind:class="{invisible : hideDateValidation}" >{{dateMessage}}</label>
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-12 text-center">
                                    <br/>
                                    <button type="submit" class="btn btn-primary px-5 py-3 rounded-pill" v-on:click="Register">Register Now</button>
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
import {ref, onMounted, defineProps, defineEmits} from 'vue';
import { useRouter } from 'vue-router';

const emit = defineEmits(['registeredManagerEvent']);

const props = defineProps({
        factory: {
            type: Object,
            required: false,
            default: null
        }
    });

const router = useRouter();

const user = ref({
    username: '',
    password: '',
    name: '',
    surname: '',
    gender: 'Male',
    dateOfBirth: '',
    role: 'Customer',
    factoryId: 0,
    isBlocked: false
})

const repeatedPassword = ref('')

const hideUsernameValidation = ref(true)
const hidePasswordValidation = ref(true)
const hideRepeatValidation = ref(true)
const hideNameValidation = ref(true)
const hideSurnameValidation = ref(true)
const hideDateValidation = ref(true)

const usernameMessage = ref(' * Field required')
const passwordMessage = ref(' * Field required')
const requiredMessage = ref(' * Field required')
const repeatMessage = ref(' * Passwords do not match')
const dateMessage = ref(' * Date required')


function Register(event){
    event.preventDefault();
    let canRegister = true

    if(user.value.username === ''){
        canRegister = false;
        hideUsernameValidation.value = false
    }
    else{
        hideUsernameValidation.value = true
    }
    if(!CheckPassword()){
        canRegister = false
    }
    if(!RepeatPassword()){
        canRegister = false
    }
    if(user.value.name === ''){
        canRegister = false;
        hideNameValidation.value = false
    }
    else{
        hideNameValidation.value = true
    }
    if(user.value.surname === ''){
        canRegister = false;
        hideSurnameValidation.value = false
    }
    else{
        hideSurnameValidation.value = true
    }
    if(user.value.dateOfBirth === ''){
        canRegister = false;
        hideDateValidation.value = false
    }
    else{
        hideDateValidation.value = true
    }

    if(canRegister){
        if(props.factory != null){
            user.value.role = 'Manager';
            props.factory.manager = user.value;
            emit('registeredManagerEvent', user)
            return;
        }

        user.value.isSuspicious = false;
        axios.post('http://localhost:8080/WebShopAppREST/rest/user/register', user.value)
            .then( response => {
                if (response.status === 200) {
                    CreateShoppingCart()
                    alert('Registration succsesfull!')
                    router.push('/login')
                }
        }).catch(error => {
        if (error.response) {
            if (error.response.status === 400) {
                let errorMsg = error.response.data
                console.log(errorMsg)
                if(errorMsg === 'Username'){
                    hideUsernameValidation.value = false;
                    usernameMessage.value = ' * Username is already taken';
                }
                else{
                    alert('Failed to register user!')
                }
            } else {
            console.error("Error response status:", error.response.status);
        }
        } else if (error.request) {
            console.error("No response received:", error.request);

        } else {
            console.error("Error setting up request:", error.message);
        }

        })
    }
}

function CheckPassword(){
    if(user.value.password === ''){
        hidePasswordValidation.value = false
        passwordMessage.value = ' * Field required';
        return false;
    }
    else if(!user.value.password.match(new RegExp('^.{1,10}$'))){
        hidePasswordValidation.value = false
        passwordMessage.value = '* Password can be max 10 characters'
        return false;
    }
    
    hidePasswordValidation.value = true

    return true;
}

function RepeatPassword(){
    if(repeatedPassword.value !== user.value.password){
        hideRepeatValidation.value = false
        return false;
    }
    
    hideRepeatValidation.value = true

    return true;
}

function CreateShoppingCart(){
    axios.post('http://localhost:8080/WebShopAppREST/rest/shopping-cart/create', user.value)
            .then( response => {
                if (response.status === 200) {
                    console.log('Shopping cart created')
                }
        }).catch(error => {
            console.error("Error response status:", error.response.status);
        })
}


</script>

<style scoped>
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
        visibility: hidden;
    }
</style>
