<template>
    <div class="container-fluid bg-light py-3">
        <div class="row border bg-light border-primary rounded-pill align-items-center mx-5 mb-3 p-4">
            <div class="col-1">
                
            </div>
            <div class="col-3">
                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Name" v-model="searchParams.name"/>
            </div>
            <div class="col-3">
                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Surname" v-model="searchParams.surname"/>
            </div>
            <div class="col-3">
                <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Username" v-model="searchParams.username"/>
            </div>
            <div class="col-1">
                <button @click="searchClick()" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-search"></i></button>
            </div>
            <div class="col-1">
                
            </div>
        </div>
        <div class="tab-class text-center">
            <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                <li class="nav-item p-2" v-on:click="openSortFilter('Filter')">
                    <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                    :class="{ 'bg-primary': showFilter, 'bg-white' : !showFilter}">
                        <span class="text-dark" style="width: 150px;">Filter <i class="fas fa-filter"></i></span>
                    </button>
                </li>
                <li class="nav-item p-2" v-on:click="openSortFilter('Sort')">
                    <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                    :class="{ 'bg-primary': showSort, 'bg-white' : !showSort}">
                        <span class="text-dark" style="width: 150px;">Sort <i class="fas fa-sort"></i></span>
                    </button>
                </li>
            </ul>
        </div>
        <div v-show="showFilter">
            <h2 class="text-center">User role <button @click="showRoleFilter = !showRoleFilter" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i :class="{ 'fas fa-angle-down': !showRoleFilter, 'fas fa-angle-up' : showRoleFilter}"></i></button></h2>
            <div v-show="showRoleFilter"class="tab-class text-center">
                <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                    <li class="nav-item p-2" v-on:click="setRoleFilter('')">
                        <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                        :class="{ 'bg-primary': searchParams.role === '', 'bg-white' : searchParams.role !== ''}">
                            <span class="text-dark" style="width: 150px;">All</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setRoleFilter('Administrator')">
                        <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                        :class="{ 'bg-primary': searchParams.role === 'Administrator', 'bg-white' : searchParams.role !== 'Administrator'}">
                            <span class="text-dark" style="width: 150px;">Administrator</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setRoleFilter('Manager')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                        :class="{ 'bg-primary': searchParams.role === 'Manager', 'bg-white' : searchParams.role !== 'Manager'}">
                            <span class="text-dark" style="width: 150px;">Manager</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setRoleFilter('Worker')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                        :class="{ 'bg-primary': searchParams.role === 'Worker', 'bg-white' : searchParams.role !== 'Worker'}">
                            <span class="text-dark" style="width: 150px;">Worker</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setRoleFilter('Customer')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                        :class="{ 'bg-primary': searchParams.role === 'Customer', 'bg-white' : searchParams.role !== 'Customer'}">
                            <span class="text-dark" style="width: 150px;">Customer</span>
                        </button>
                    </li>
                </ul>
            </div>
            <h2 class="text-center">User type <button @click="showTypeFilter = !showTypeFilter" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i :class="{ 'fas fa-angle-down': !showTypeFilter, 'fas fa-angle-up' : showTypeFilter}"></i></button></h2>
            <div v-show="showTypeFilter"class="tab-class text-center">
                <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                    <li class="nav-item p-2" v-on:click="setTypeFilter('All')">
                        <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                        :class="{ 'bg-primary': !searchParams.blocked && !searchParams.sus, 'bg-white' : searchParams.blocked || searchParams.sus}">
                            <span class="text-dark" style="width: 150px;">All</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setTypeFilter('Blocked')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                        :class="{ 'bg-primary': searchParams.blocked, 'bg-white' : !searchParams.blocked}">
                            <span class="text-dark" style="width: 150px;">Blocked</span>
                        </button>
                    </li>
                    <li class="nav-item p-2" v-on:click="setTypeFilter('Sus')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                        :class="{ 'bg-primary': searchParams.sus, 'bg-white' : !searchParams.sus}">
                            <span class="text-dark" style="width: 150px;">Suspicious</span>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
        <div v-if="showSort" class="row d-flex justify-content-center px-3 py-3">
            <div class="d-flex justify-content-around align-items-center">
                <div class="col-3 d-flex justify-content-center align-items-center">
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameDown()">
                        <i class="fas fa-sort-down"></i>
                    </a>
                    <label class="text-dark border-bottom border-primary">
                        Name
                    </label>
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameUp()">
                        <i class="fas fa-sort-up"></i>
                    </a>
                </div>

                <div class="col-3 d-flex justify-content-center align-items-center">
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameDown()">
                        <i class="fas fa-sort-down"></i>
                    </a>
                    <label class="text-dark border-bottom border-primary">
                        Surname
                    </label>
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameUp()">
                        <i class="fas fa-sort-up"></i>
                    </a>
                </div>

                <div class="col-3 d-flex justify-content-center align-items-center mx-2">
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortLocationDown()">
                        <i class="fas fa-sort-down"></i>
                    </a>
                    <label class="text-dark border-bottom border-primary">
                        Username
                    </label>
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortLocationUp()">
                        <i class="fas fa-sort-up"></i>
                    </a>
                </div>

                <div class="col-3 d-flex justify-content-center align-items-center">
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortRatingDown()">
                        <i class="fas fa-sort-down"></i>
                    </a>
                    <label class="text-dark border-bottom border-primary">
                        Customer points
                    </label>
                    <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortRatingUp()">
                        <i class="fas fa-sort-up"></i>
                    </a>
                </div>

            </div>
        </div>
    </div>
    <div v-if="users" class="container-fluid team py-6">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Users</small>
                <h1 class="display-5 mb-5">Users</h1>
            </div>
            <div class="row g-4">
                <UserCard v-for="user in users" :user="user" @blockUser="blockUser"/>
            </div>
        </div>
    </div>
</template>

<script setup>
    import UserCard from '@/components/UserCard.vue';
    import { ref, onMounted } from 'vue';
    import axios from 'axios';

    const users = ref([]);

    const showFilter = ref(false);
    const showTypeFilter = ref(false);
    const showRoleFilter = ref(false);
    const showSort = ref(false);

    function openSortFilter(param){
        switch(param){
            case 'Filter':
                showFilter.value = !showFilter.value;
                if(showSort.value)
                    showSort.value = !showSort.value
                break;
            case 'Sort':
                showSort.value = !showSort.value;
                if(showFilter.value)
                    showFilter.value = !showFilter.value
                break;
            default:
                break;
        }
    }

    function setRoleFilter(role){
        searchParams.value.role = role;
        loadUsers()
    }

    function searchClick(){
        loadUsers();
    }

    function setTypeFilter(type){
        switch(type){
            case 'All':
                searchParams.value.blocked = false;
                searchParams.value.sus = false;
                break;
            case 'Blocked':
                searchParams.value.blocked = !searchParams.value.blocked;
                break;
            case 'Sus':
                searchParams.value.sus = !searchParams.value.sus;
                break;
            default:
                break;
        }

        loadUsers();
    }

    const searchParams = ref({
        name: '',
        surname: '',
        username: '',
        role: '',
        sus: false,
        blocked: false
    });

    onMounted(async () => {
        await loadUsers();
    });

    function blockUser(id){
        try {
            axios.post(`http://localhost:8080/WebShopAppREST/rest/user/block/${id}`, {}, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }
            }).then(() => loadUsers());    
        } catch (error) {
            console.error("Error blocking user: ", error);
        }
    }

    async function loadUsers(){
        try {
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/user/search', searchParams.value);
            users.value = response.data;
            console.log(response.data);
        } catch (error) {
            console.error("Error loading users: ", error);
        }
    }
</script>