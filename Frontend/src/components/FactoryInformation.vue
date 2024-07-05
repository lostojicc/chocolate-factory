<template>
    <div class="container-fluid footer py-5 mb-0 bg-light wow bounceInUp" data-wow-delay="0.1s">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-3 col-md-4 d-flex justify-content-center">
                        <img :src="factory.imagePath" class="img-fluid rounded footer-img" alt="">
                </div>
                <div class="col-lg-3 col-md-4">
                    <div class="footer-item">
                        <h1 class="text-dark">{{ factory.name }}</h1>
                        <div class="footer-item">
                            <div class="d-flex align-items-center">
                            <i v-for="n in filledStars" class="fas fa-star text-primary"></i>
                            <i v-for="n in emptyStars" class="far fa-star text-primary"></i>
                            <h5 class="m-2"> {{ factory.rating.toFixed(2) }}</h5>
                        </div>
                    </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-4 d-flex justify-content-center">
                    <div class="footer-item">
                        <h4 class="mb-4">Find Us</h4>
                        <div class="d-flex flex-column align-items-start">
                            <p><i class="fa fa-map-marker-alt text-primary me-2"></i>{{ factory.location.address.street }}, {{ factory.location.address.city }}, {{ factory.location.address.state }}</p>
                            <p><i class="fa fa-clock text-primary me-2"></i>{{ factory.openTime }} - {{ factory.closeTime }}</p>
                            <p><i v-if="factory.status === 'CLOSED'" class="fas fa-door-closed text-primary me-2"></i><i v-else class="fas fa-door-open text-primary me-2"></i>{{ factory.status }}</p>
                        </div>
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

<style scoped>
    .footer-img{
        height: 150px;
    }
</style>