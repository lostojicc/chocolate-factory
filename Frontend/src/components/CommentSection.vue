<template>
    <div class="container-fluid py-6">
            <div class="container">
                <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Comments</small>
                    <h1 class="display-5 mb-5">What Our Customers say!</h1>
                </div>
                <CommentCard v-for="comment in comments" :comment="comment"/>
            </div>
        </div>
</template>

<script setup>
    import CommentCard from '../components/CommentCard.vue';
    import { defineProps } from 'vue';
    import {ref, onMounted } from 'vue';
    import axios from 'axios';

    const props = defineProps({
        factory: {
            type: Object,
            required: true
        }
    });

    const comments = ref([]);

    onMounted(async () => {
        await loadComments();
    });

    async function loadComments(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/${props.factory.id}`);
            comments.value = response.data;
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }
</script>