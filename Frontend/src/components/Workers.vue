<template>
    <div class="container-fluid menu py-6">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Workers</small>
                <h1 class="display-5 mb-5">This factory's workers
                    <a v-if="userRole === 'Manager'" class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="openForm = !openForm"><i class="fas fa-plus"></i></a>
                </h1>
            </div>
                <RegisterForm v-if="openForm" @addedWorkerEvent="handleAddEvent" :factory="factory"/>
            <div class="tab-class text-center">
            <div class="tab-content">
                <div id="tab-6" class="tab-pane fade show p-0 active">
                    <div class="row g-4">
                        <UserCard v-for="worker in workers" :user="worker" @deleteWorker="deleteWorker"/>
                    </div>
                </div>
            </div>
        </div>
        </div>   
    </div>
</template>

<script setup>
    import { ref, onMounted, defineProps } from 'vue';
    import axios from 'axios';
    import RegisterForm from '@/components/RegisterForm.vue';
    import UserCard from './UserCard.vue';

    const openForm = ref(false);

    const props = defineProps({
        factory: {
            type: Object,
            required: true
        }
    });

    onMounted(async () => {
        loadWorkers();
    });

    function deleteWorker(id){
        axios.delete(`http://localhost:8080/WebShopAppREST/rest/factory/fire/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
            }
        }).then(() => loadWorkers()).catch(error => {
            console.error(error.response.data);
        });
    }

    function handleAddEvent(user){
        axios.post('http://localhost:8080/WebShopAppREST/rest/user/register', user.value)
            .then( response => {
                if (response.status === 200) {
                    loadWorkers();
                    openForm.value = false;
                }
        }).catch(error => {
        if (error.response) {
            if (error.response.status === 400) {
                let errorMsg = error.response.data
                console.log(errorMsg)
                if(errorMsg === 'Username'){
                    console.error('Username taken!');
                }
                else{
                    alert('Failed to register Worker!')
                }
            } else {
            console.error("Error response status:", error.response.status);
        }
        } else if (error.request) {
            console.error("No response received:", error.request);

        } else {
            console.error("Error setting up request:", error.message);
        }

        })
    }

    async function loadWorkers(){
        if(userRole.value != 'Manager')
            return;
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/user/getWorkers/${props.factory.id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }
            });
            console.log(response.data);
            workers.value = response.data;
        } catch (error) {
            console.error('Error loading workers: ', error);
        }
    }

    const workers = ref([]);
    const userRole = ref(localStorage.getItem('role') || '');
    const username = ref(localStorage.getItem('username') || '');
</script>