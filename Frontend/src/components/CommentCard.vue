<template>
    <div v-if="user" class="container">
        <div class="card mb-3 container border border-primary">
            <div class="row g-0 p-2">
                <div class="col-md-4 d-flex">
                    <div>
                        <img src="../img/testimonial-2.jpg" class="img-fluid rounded-circle" alt="User Image">
                        <div class="mt-2 text-center mx-1">
                            <div class="d-flex">
                                <i v-for="n in comment.grade" class="fas fa-star text-primary"></i>
                                <i v-for="n in (5 - comment.grade)" class="far fa-star text-primary"></i>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex flex-column justify-content-start align-items-start mt-2 ps-3">
                        <h4 class="mb-0">{{ user.name }} {{ user.surname }}</h4>
                        <p class="mb-0">{{ user.username }}</p>
                        <h5 v-if="userRole === 'Manager' || userRole=== 'Administrator'" class="mb-0 mt-2 text-secondary">{{ comment.state }}</h5>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="d-flex mt-2 justify-content-between">
                        <p class="card-text fs-5">{{ comment.text }}</p>
                    </div>
                </div>
                <div class="col-md-2 d-flex flex-column justify-content-center align-items-end">
                    <button v-if="userRole ==='Manager' && comment.state==='Pending' && editable" type="submit" class="d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill fixed-size-button" 
                v-on:click="AcceptClick()">
                    <i class="fas fa-check fa-lg px-2"></i>
                    Accept
                </button>
                <button v-if="userRole ==='Manager' && comment.state==='Pending' && editable" type="submit" class="mt-3 d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill fixed-size-button" 
                v-on:click="RejectClick()">
                    <i class="fas fa-times fa-lg px-2"></i>
                    Reject
                </button> 
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps, onMounted, ref } from 'vue';
    import axios from 'axios';

    const emit = defineEmits(['loadEvent']);
    const props = defineProps({
        comment: {
            type: Object,
            required: true
        },
        editable: {
            type: Boolean,
            required: true
        }
    });

    const userRole = localStorage.getItem('role') || '';
    const username = localStorage.getItem('username') || '';

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

    function AcceptClick(){
        SendRequest(1)
    }

    function RejectClick(){
        SendRequest(0)
    }

    async function SendRequest(logic){
        try {
            const response = await axios.post(`http://localhost:8080/WebShopAppREST/rest/factory/comments/acceptOrReject/${logic}`, props.comment ,{
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            emit('loadEvent', null);
        } catch (error) {
            console.error('Error accepting/rejecting comment:', error);
        }
    }

</script>

<style scoped>
.fixed-size-button {
    width: 150px; /* Adjust width as needed */
    height: 50px; /* Adjust height as needed */
}
</style>