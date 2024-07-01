<template>
    <div class="container">
        <div class="row g-5 align-items-center mb-5">
            <div class="col-lg-4 wow bounceInUp" data-wow-delay="0.1s">
                <img :src="factory.imagePath" class="img-fluid rounded" alt="">
            </div>
            <div class="col-lg-8 wow bounceInUp" data-wow-delay="0.3s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">{{ factory.status }}</small>
                <h1 class="display-5 mb-4">{{ factory.name }}</h1>
                <p class="mb-4">Consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore eit esdioilore magna aliqua. Ut enim ad minim veniam, 
                    quis nostrud exercitation ullaemco laboeeiris nisi ut aliquip ex ea commodo consequat. Duis aute irure 
                    dolor iesdein reprehendeerit in voluptate velit esse cillum dolore.</p>
                <div class="row g-4 text-dark mb-5">
                    <div class="col-md-6">
                        <div class="d-inline-flex justify-content-center w-100 border border-primary p-4 rounded mb-4">
                            <i class="fas fa-map-marker-alt fa-2x text-primary me-4"></i>
                            <div class="">
                                <h4>Address</h4>
                                <p>{{ factory.address.street }}, {{ factory.address.city }}, {{ factory.address.state }}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 border border-primary p-4 mb-4 rounded"> 
                        <div class="d-flex justify-content-center align-items-center">
                            <h4>Rating:</h4>
                        </div>
                        <div class="d-flex justify-content-center align-items-center">
                            <i v-for="n in filledStars" class="fas fa-star text-primary"></i>
                            <i v-for="n in emptyStars" class="far fa-star text-primary"></i>
                            <h5 class="m-2"> {{ factory.rating }}</h5>
                        </div>     
                    </div>
                    <div class="col-md-6">
                        <RouterLink :to="{ name: 'factory', params: { id: factory.id } }" class="btn btn-primary py-3 px-5 rounded-pill">About Us<i class="fas fa-arrow-right ps-2"></i></RouterLink>
                    </div>
                    
                </div>
                
            </div>
        </div>
        
    </div>
</template>

<script setup>
    import { defineProps, computed } from 'vue';

    const props = defineProps({
    factory: {
        type: Object,
        required: true
    }
    });

    const filledStars = computed(() => Math.round(props.factory.rating));
    const emptyStars = computed(() => 5 - filledStars.value);
</script>