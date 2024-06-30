<template>
    <div class="container-fluid py-6">
            <div class="container">
                <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Comments</small>
                    <h1 class="display-5 mb-5">What Our Customers say!</h1>
                </div>
                <CommentCard v-for="comment in comments" :comment="comment" @loadEvent="loadComments()"/>
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

    const userRole = localStorage.getItem('role') || '';
    const username = localStorage.getItem('username') || '';

    const comments = ref([]);

    onMounted(async () => {
        await loadComments();
    });

    async function loadComments(){
        if(userRole !== 'Manager' && userRole !== 'Administrator'){
            acceptedComments()
        }
        else{
            allComments()
        }
    }

    async function acceptedComments(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/acceptedComments/${props.factory.id}`);
            comments.value = response.data;
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }

    async function allComments(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/${props.factory.id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            comments.value = response.data;
            sortComments()
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }

    function sortComments() {
        comments.value.sort((a, b) => {
            const stateOrder = { Pending: 1, Accepted: 2, Rejected: 3 };
            return stateOrder[a.state] - stateOrder[b.state];
        });
    }

</script>