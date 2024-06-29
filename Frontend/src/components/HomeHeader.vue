<template>
    <div class="container-fluid bg-light py-6 my-6 mt-0">
        <div class="container">
            <div class="row g-5 align-items-center">
                <div class="col-lg-7 col-md-12">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-4 animated bounceInDown">Welcome!</small>
                    <h1 class="display-1 mb-4 animated bounceInDown">Scroll to check out all factories! <RouterLink v-if="userRole == 'Administrator'" :to="'/add-factory'" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-plus"></i></RouterLink></h1>
                    <form>
                        <div class="row border bg-light border-primary rounded-pill align-items-center p-4">
                            <div class="col-3">
                                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Name"/>
                            </div>
                            <div class="col-3">
                                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Chocolate"/>
                            </div>
                            <div class="col-3">
                                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Location"/>
                            </div>
                            <div class="col-2">
                                <input type="number" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Rating"/>
                            </div>
                            <div class="col-1">
                                <button class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-search"></i></button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-lg-5 col-md-12">
                    <img src="../img/homepagec.png" class="img-fluid rounded animated zoomIn" alt="">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref} from 'vue';

    const userRole = ref(localStorage.getItem('role') || '');
</script>