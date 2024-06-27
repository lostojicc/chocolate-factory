<script setup>
    import { RouterLink, RouterView, useRouter } from 'vue-router'
    import { ref, computed, reactive, onMounted } from 'vue';

    const router = useRouter();

    onMounted(() => {
        router.push("/");
    });

    const userRole = ref(localStorage.getItem('role') || '');
    const username = ref(localStorage.getItem('username') || '');

    function signOut(){
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        location.reload();
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
                            <RouterLink class="nav-item nav-link active" :to="'/'">Home</RouterLink>
                            <!-- <RouterLink class="nav-item nav-link" :to="'/factory'">About</RouterLink>
                            <a href="service.html" class="nav-item nav-link">Services</a>
                            <a href="event.html" class="nav-item nav-link">Events</a>
                            <a href="menu.html" class="nav-item nav-link">Menu</a>
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                                <div class="dropdown-menu bg-light">
                                    <a href="book.html" class="dropdown-item">Booking</a>
                                    <a href="blog.html" class="dropdown-item">Our Blog</a>
                                    <a href="team.html" class="dropdown-item">Our Team</a>
                                    <a href="testimonial.html" class="dropdown-item">Testimonial</a>
                                    <a href="404.html" class="dropdown-item">404 Page</a>
                                </div>
                            </div>
                            <a href="contact.html" class="nav-item nav-link">Contact</a> -->
                        </div>
                        
                        <RouterLink v-if="userRole === ''" :to="'/login'"><a href="" class="btn btn-primary py-2 px-4 d-none d-xl-inline-block rounded-pill">Sign In</a></RouterLink>
                        <span v-else class="d-none d-lg-inline-flex justify-content-center align-items-center">{{ username }} | {{ userRole }}
                            <div class="nav-item dropdown">
                                <a href="#" class="btn-search btn btn-primary btn-md-square ms-4 w-75 rounded-pill d-flex justify-content-between d-lg-inline-flex" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i>
                                    <i class="fas fa-chevron-down"></i>
                                </a>
                            <div class="dropdown-menu bg-light">
                                <RouterLink class="dropdown-item" :to="'/profile'">Profile</RouterLink>
                                <RouterLink v-if="userRole === 'Customer'" class="dropdown-item" :to="'/'">Orders</RouterLink>
                            </div>
                            </div>
                        </span>
                        <RouterLink v-if="userRole === 'Customer'" :to="'/shopping-cart'"><button class="btn-search btn btn-primary btn-md-square ms-4 me-4 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-shopping-cart"></i></button></RouterLink>
                        <button @click="signOut()" class="btn-search btn btn-primary btn-md-square ms-4 me-4 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-download"></i></button>
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
