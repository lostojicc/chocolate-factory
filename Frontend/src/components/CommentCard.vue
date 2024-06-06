<template>
    <div v-if="user" class="owl-carousel testimonial-carousel testimonial-carousel-2 wow bounceInUp" data-wow-delay="0.3s">
        <div class="testimonial-item rounded bg-light">
            <div class="d-flex mb-3">
                <img src="../img/testimonial-2.jpg" class="img-fluid rounded-circle flex-shrink-0" alt="">
                <!-- <div class="position-absolute" style="top: 15px; right: 20px;">
                    <i class="fa fa-quote-right fa-2x"></i>
                </div> -->
                <div class="ps-3 my-auto">
                    <h4 class="mb-0">{{ user.name }} {{ user.surname }}</h4>
                    <p class="m-0">{{ user.username }}</p>
                </div>
            </div>
            <div class="testimonial-content">
                <div class="d-flex">
                    <i v-for="n in comment.grade" class="fas fa-star text-primary"></i>
                    <i v-for="n in (5 - comment.grade)" class="far fa-star text-primary"></i>
                </div>
                <p class="fs-5 m-0 pt-3">{{ comment.text }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps, onMounted, ref } from 'vue';
    import axios from 'axios';

    const props = defineProps({
        comment: {
            type: Object,
            required: true
        }
    });

    const user = ref(null);

    onMounted(async () => {
        await loadUser();
    });

    async function loadUser(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/user/${props.comment.userId}`);
            user.value = response.data;
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }

</script>