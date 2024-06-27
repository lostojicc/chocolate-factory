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
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Name" v-model="user.username"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="editClick()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div class="col-2">
                                    <label class="text-dark">Gender</label>
                                    <select class="form-select border-primary p-2" aria-label="Gender" v-model="user.gender">
                                        <option value="Male" selected>Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <label class="text-dark">Date of Birth</label>
                                    <input type="date" class="form-control border-primary p-2" placeholder="Birth Date" v-model="user.dateOfBirth"/>
                                </div>

                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-between">
                                    <label class="text-dark" > Surname: </label>
                                    <div class="d-flex align-items-center">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Surname" v-model="user.username"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="editClick()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-start">
                                    <label class="text-dark" > Number of points: 
                                    </label>
                                    <h5 class="text-primary font-weight-bold m-2"> 1000</h5>
                                </div>

                                <div class="col-1">
                                </div>

                            </div>
                            <div class="row text-center mb-1 mt-5">
                                <div class="m-2">
                                    <div class="border-bottom border-primary d-inline-block"><h4>Profile information:</h4></div>
                                </div>
                            </div>
                            <div v-if="isPasswordCorrect === 0 || isPasswordCorrect === 2" class="row form mt-1">
                                <div class="col-3"></div>
                                <div class="col-6 d-flex align-items-center justify-content-center">
                                    <label>Enter password to show information</label>
                                    <input type="password" class="form-control border-primary p-2" placeholder="Enter Your Password" v-model="repeatedPassword">
                                </div>
                                <div class="col-3"></div>

                                <div class="col-12 text-center my-4">
                                    <button type="submit" class="btn btn-primary px-4 py-2 rounded-pill" v-on:click="Register">Submit</button>
                                </div>
                            </div>
                            
                            <div v-if="isPasswordCorrect === 1" class="row form my-4">
                                <div class="col-1">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <label class="text-dark" >Username: </label>
                                    <div class="d-flex align-items-center mx-1">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Enter Your Username" v-model="user.username"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="editClick()">
                                            <i class="fas fa-check"></i>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-2">
                                </div>

                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <label class="text-dark" >Password: </label>
                                    <div class="d-flex align-items-center mx-1">
                                        <input type="text" class="form-control border-primary p-2 w-60" placeholder="Change Your Password" v-model="user.username"/>
                                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle text-center" @click="editClick()">
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


const router = useRouter();

const user = ref({
    username: '',
    password: '',
    name: '',
    surname: '',
    gender: 'Male',
    dateOfBirth: '',
    role: 'Customer',
})

const isPasswordCorrect = ref(0)


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
        font-size: medium;
        color: red;
    }
    .invisible{
        visibility: hidden;
    }
</style>
