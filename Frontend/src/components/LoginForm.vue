<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
        <div v-if="errorMessage != ''" class="container border border-primary bg-light align-items-center mb-3">
            <div class="row">
                <div class="col-1 d-flex align-items-center">
                    <i class="fas fa-info-circle" style="color: red;"></i>
                </div>
                <div class="col-10 d-flex justify-content-center align-items-center text-center">
                    <p class="my-3">{{ errorMessage }}</p>
                </div>
                <div class="col-1 d-flex align-items-center">
                    <button @click="dismissError" class="btn"><i class="fas fa-times"></i></button>
                </div>
            </div>
        </div>
            <div class="container">
                <div class="row g-0">
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-start" style="object-fit: cover; opacity: 0.7;" alt="">
                    </div>
                    <div class="col-10">
                        <div class="border-bottom border-top border-primary bg-light py-5 px-4">
                            <div class="text-center">
                                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Welcome</small>
                                <h1 class="display-5 mb-5">Sign in using your credentials</h1>
                            </div>
                            <div class="row g-4 form">
                                <div class="col-lg-3 col-md-6">
    
                                </div>
                                <div class="col-lg-6 col-md-6">                            
                                        <input type="text" v-model="user.username" class="form-control border-primary p-2" placeholder="Enter Your Username">                  
                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <input type="password" v-model="user.password" class="form-control border-primary p-2" placeholder="Enter Your Password">
                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col">
                                </div>
                                <div class="col">
                                </div>
                                <div class="col text-center stackpanel">
                                    <button @click="signIn" type="submit" class="btn btn-primary px-5 py-3 rounded-pill">Sign In</button>
                                    <br/>
                                    <label>Don't have an account? <router-link :to="'/register'"><b>Register now.</b></router-link></label>
                                </div>
                                <div class="col">
                                </div>
                                <div class="col">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-end" style="object-fit: cover; opacity: 0.7;" alt="">
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>
    import axios from 'axios';
    import { ref, onMounted } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();
    const invalidUsername = ref("");
    const invalidPassword = ref("");
    
    const errorMessage = ref('');

    const user = ref({
        username : '',
        password : ''
    });

    function decodeToken(token){
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    }

    function dismissError(){
        errorMessage.value = '';
    }

    function signIn(event){
        event.preventDefault();
        console.log(user.value);
        axios.post("http://localhost:8080/WebShopAppREST/rest/login", user.value).then(response => {
            let token = response.data;
            let decodedToken = decodeToken(token);

            let username = decodedToken.sub;
            let role = decodedToken.role;

            localStorage.setItem('jwtToken', token);
            localStorage.setItem('username', username);
            localStorage.setItem('role', role);
            location.reload();
        }).catch(error => {
            errorMessage.value = error.response.data;
            console.log(error.response.data)
        });
    }

    
</script>

<style scoped>
    .stackpanel{
        display: flex;
        flex-direction: column;
    }
</style>