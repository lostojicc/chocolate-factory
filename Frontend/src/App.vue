<script setup>
    import { RouterLink, RouterView, useRouter } from 'vue-router'
    import { ref, computed, reactive, onMounted } from 'vue';
    import axios from 'axios';

    const router = useRouter();

    onMounted(async () => {
        await loadFactory();
        router.push("/");
    });

    const factoryId = ref(0)

    const userRole = ref(localStorage.getItem('role') || '');
    const username = ref(localStorage.getItem('username') || '');

    function signOut(){
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        location.reload();
    }

    async function loadFactory(){
        try {
            if(userRole.value === 'Manager' || userRole.value === 'Worker'){
                const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/getFactoryByUsername/${username.value}`);
                factoryId.value = response.data
            }
        } catch (error) {
            console.error('Error loading factory:', error);
        } 
    }
</script>

<template>
  <header>
    <div class="container-fluid nav-bar">
            <div class="container">
                <nav class="navbar navbar-light navbar-expand-lg py-4">
                    <RouterLink class="navbar-brand" :to="'/'">
                        <h1 class="text-primary fw-bold mb-0"><span class="text-dark">RA/</span>27-29<span class="text-dark">/2021</span> </h1>
                    </RouterLink>
                    <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                        <span class="fa fa-bars text-primary"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <div class="navbar-nav mx-auto">
                            <RouterLink class="nav-item nav-link active me-2" :to="'/'">Home</RouterLink>
                            <RouterLink v-if="userRole === 'Manager' || userRole === 'Worker'" class="nav-item nav-link active" :to="{ name: 'factory', params: { id: factoryId } }">My Factory</RouterLink>
                            <RouterLink v-if="userRole === 'Customer'" class="nav-item nav-link active" :to="'/orders'">Orders</RouterLink>
                        </div>
                        
                        <RouterLink v-if="userRole === ''" :to="'/login'"><a href="" class="btn btn-primary py-2 px-4 d-none d-xl-inline-block rounded-pill">Sign In</a></RouterLink>
                        <span v-else class="d-none d-lg-inline-flex justify-content-center align-items-center">{{ username }} | {{ userRole }}
                            <div class="nav-item dropdown">
                                <a href="#" class="btn-search btn btn-primary btn-md-square ms-4 w-75 rounded-pill d-flex justify-content-between" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i>
                                    <i class="fas fa-chevron-down"></i>
                                </a>
                            <div class="dropdown-menu bg-light p-2" style="min-width: 100px;">
                                <RouterLink class="dropdown-item" :to="'/profile'">Profile</RouterLink>
                                <a href="#" class="dropdown-item" @click="signOut()">Sign out</a>
                            </div>
                            </div>
                        </span>
                        <RouterLink v-if="userRole === 'Customer'" :to="'/shopping-cart'"><button class="btn-search btn btn-primary btn-md-square ms-4 me-4 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-shopping-cart"></i></button></RouterLink>
                    </div>
                </nav>
            </div>
        </div>
  </header> 

  <RouterView />
</template>

<style>
  .nav-link .fas.fa-user {
    display: inline-block !important; /* Ensure the icon is always displayed */
  }

  .dropdown-toggle::after {
    display: none; /* Optionally, hide the dropdown indicator arrow */
  }
</style>
