<template>
    <div class="col-lg-3 col-md-6 wow bounceInUp" data-wow-delay="0.1s">
        <div class="team-item rounded">
            <img class="img-fluid rounded-top" src="../img/new-user.jpg" alt="">
            <div class="team-content text-center py-3 bg-dark rounded-bottom">
                <h4 class="text-primary" :class="{ 'text-decoration-line-through' : user.blocked }">{{ user.name }} {{ user.surname }}</h4>
                <p class="text-white mb-0" :class="{ 'text-decoration-line-through' : user.blocked }">@{{ user.username }} - {{ user.role }}</p>
            </div>
            <div class="team-icon d-flex flex-column justify-content-center m-4">
                <button v-if="userRole == 'Administrator' && user.role != 'Administrator'" @click="blockUser" class="share btn btn-primary btn-md-square mb-2 rounded-circle d-none d-lg-inline-flex" href=""><i :class="{ 'fas fa-ban' : !user.blocked, 'fas fa-check-circle' : user.blocked }"></i></button>
                <button v-if="(userRole == 'Administrator' && user.role != 'Administrator') || (userRole == 'Manager' && user.role == 'Worker')" @click="deleteUser" class="btn btn-primary btn-md-square mb-2 rounded-circle d-none d-lg-inline-flex" :class="{ 'share-link' : userRole == 'Administrator', 'share' : userRole == 'Manager'}" href=""><i class="fas fa-user-slash"></i></button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, defineProps, defineEmits} from 'vue';

    const userRole = ref(localStorage.getItem('role') || '');
    const username = ref(localStorage.getItem('username') || '');

    function setMessage(){
        return props.user.blocked ? 'unblock' : 'block';
    }

    function blockUser(){
        var message = setMessage();

        if(confirm("Are you sure you want to " + message + " this user?") == true)
            emit('blockUser', props.user.id)
    }
    
    const props = defineProps({
        user:{
            type: Object,
            required: true
        }
    });

    const emit = defineEmits(['blockUser']);
</script>