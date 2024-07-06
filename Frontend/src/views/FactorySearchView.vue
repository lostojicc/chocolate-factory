<template>
    <div v-if="searchParams != null">
        <div class="container-fluid bg-light py-3">
            <div class="row border bg-light border-primary rounded-pill align-items-center mx-5 mb-3 p-4">
                <div class="col-3">
                    <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Name" v-model="searchParams.name"/>
                </div>
                <div class="col-3">
                    <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Chocolate" v-model="searchParams.chocolate"/>
                </div>
                <div class="col-3">
                    <input type="text" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Location" v-model="searchParams.location"/>
                </div>
                <div class="col-2">
                    <input type="number" min="0" max="5" class="border form-control border-primary p-2 rounded-pill height w-20" placeholder="Rating" v-model="searchParams.rating"/>
                </div>
                <div class="col-1">
                    <button @click="searchClick()" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fas fa-search"></i></button>
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
                <h2 class="text-center">Chocolate kind <button @click="showKindFilter = !showKindFilter" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i :class="{ 'fas fa-angle-down': !showKindFilter, 'fas fa-angle-up' : showKindFilter}"></i></button></h2>
                <div v-show="showKindFilter"class="tab-class text-center">
                    <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                        <li class="nav-item p-2" v-on:click="setKindFilter('Classic')">
                            <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                            :class="{ 'bg-primary': searchParams.chocolateKind === 'Classic', 'bg-white' : searchParams.chocolateKind !== 'Classic'}">
                                <span class="text-dark" style="width: 150px;">Classic</span>
                            </button>
                        </li>
                        <li class="nav-item p-2" v-on:click="setKindFilter('Cooking')">
                            <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                            :class="{ 'bg-primary': searchParams.chocolateKind === 'Cooking', 'bg-white' : searchParams.chocolateKind !== 'Cooking'}">
                                <span class="text-dark" style="width: 150px;">Cooking</span>
                            </button>
                        </li>
                        <li class="nav-item p-2" v-on:click="setKindFilter('Drinking')">
                            <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                            :class="{ 'bg-primary': searchParams.chocolateKind === 'Drinking', 'bg-white' : searchParams.chocolateKind !== 'Drinking'}">
                                <span class="text-dark" style="width: 150px;">Drinking</span>
                            </button>
                        </li>
                    </ul>
                </div>
                <h2 class="text-center">Chocolate type <button @click="showTypeFilter = !showTypeFilter" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i :class="{ 'fas fa-angle-down': !showTypeFilter, 'fas fa-angle-up' : showTypeFilter}"></i></button></h2>
                <div v-show="showTypeFilter"class="tab-class text-center">
                    <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                        <li class="nav-item p-2" v-on:click="setTypeFilter('Black')">
                            <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                            :class="{ 'bg-primary': searchParams.chocolateType === 'Black', 'bg-white' : searchParams.chocolateType !== 'Black'}">
                                <span class="text-dark" style="width: 150px;">Black</span>
                            </button>
                        </li>
                        <li class="nav-item p-2" v-on:click="setTypeFilter('Milky')">
                            <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                            :class="{ 'bg-primary': searchParams.chocolateType === 'Milky', 'bg-white' : searchParams.chocolateType !== 'Milky'}">
                                <span class="text-dark" style="width: 150px;">Milky</span>
                            </button>
                        </li>
                        <li class="nav-item p-2" v-on:click="setTypeFilter('White')">
                            <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                            :class="{ 'bg-primary': searchParams.chocolateType === 'White', 'bg-white' : searchParams.chocolateType !== 'White'}">
                                <span class="text-dark" style="width: 150px;">White</span>
                            </button>
                        </li>
                    </ul>
                </div>
                <h2 class="text-center">Factory status <button @click="showStatusFilter = !showStatusFilter" class="btn-search btn btn-primary btn-md-square me-2 rounded-circle d-none d-lg-inline-flex" data-bs-toggle="modal" data-bs-target="#searchModal"><i :class="{ 'fas fa-angle-down': !showStatusFilter, 'fas fa-angle-up' : showStatusFilter}"></i></button></h2>
                <div v-show="showStatusFilter"class="tab-class text-center">
                    <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                        <li class="nav-item p-2" v-on:click="setStatusFilter('OPEN')">
                            <button class="d-flex py-2 mx-2 border border-primary  rounded-pill active" data-bs-toggle="pill" 
                            :class="{ 'bg-primary': searchParams.status === 'OPEN', 'bg-white' : searchParams.status !== 'OPEN'}">
                                <span class="text-dark" style="width: 150px;">Open</span>
                            </button>
                        </li>
                        <li class="nav-item p-2" v-on:click="setStatusFilter('CLOSED')">
                            <button class="d-flex py-2 mx-2 border border-primary rounded-pill" data-bs-toggle="pill"
                            :class="{ 'bg-primary': searchParams.status === 'CLOSED', 'bg-white' : searchParams.status !== 'CLOSED'}">
                                <span class="text-dark" style="width: 150px;">Closed</span>
                            </button>
                        </li>
                    </ul>
                </div>
            </div>
            <div v-if="showSort" class="row d-flex justify-content-center px-3 py-3">
                <div class="d-flex justify-content-around align-items-center">
                    <div class="col-4 d-flex justify-content-center align-items-center">
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameDown()">
                            <i class="fas fa-sort-down"></i>
                        </a>
                        <label class="text-dark border-bottom border-primary">
                            Factory Name
                        </label>
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortNameUp()">
                            <i class="fas fa-sort-up"></i>
                        </a>
                    </div>

                    <div class="col-4 d-flex justify-content-center align-items-center mx-2">
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortLocationDown()">
                            <i class="fas fa-sort-down"></i>
                        </a>
                        <label class="text-dark border-bottom border-primary">
                            Location
                        </label>
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortLocationUp()">
                            <i class="fas fa-sort-up"></i>
                        </a>
                    </div>

                    <div class="col-4 d-flex justify-content-center align-items-center">
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortRatingDown()">
                            <i class="fas fa-sort-down"></i>
                        </a>
                        <label class="text-dark border-bottom border-primary">
                            Rating
                        </label>
                        <a class="btn btn-primary btn-sm-square m-2 rounded-circle d-flex  align-items-center justify-content-center" @click="SortRatingUp()">
                            <i class="fas fa-sort-up"></i>
                        </a>
                    </div>

                </div>
            </div>
        </div>  
        <div class="container-fluid py-3 mb-3">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Search</small>
                <h1 class="display-5 mb-5">Search results</h1>
            </div>
            <div class="tab-class text-center">
                <ul class="nav nav-pills d-inline-flex justify-content-center mb-2 wow bounceInUp" data-wow-delay="0.1s">
                    <li v-show="searchParams.chocolateKind != undefined && searchParams.chocolateKind !== ''" class="nav-item p-2" v-on:click="removeFilter('Kind')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill active" data-bs-toggle="pill">
                            <span class="text-dark" style="width: 150px;">chocolate-kind: {{ searchParams.chocolateKind }} <i class="fas fa-times ms-5"></i></span>
                        </button>
                    </li>
                    <li v-show="searchParams.chocolateType != undefined && searchParams.chocolateType !== ''" class="nav-item p-2" v-on:click="removeFilter('Type')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill active" data-bs-toggle="pill">
                            <span class="text-dark" style="width: 150px;">chocolate-type: {{ searchParams.chocolateType }} <i class="fas fa-times ms-5"></i></span>
                        </button>
                    </li>
                    <li v-show="searchParams.status != undefined && searchParams.status !== ''" class="nav-item p-2" v-on:click="removeFilter('Status')">
                        <button class="d-flex py-2 mx-2 border border-primary rounded-pill active" data-bs-toggle="pill">
                            <span class="text-dark" style="width: 150px;">factory-status: {{ searchParams.status }} <i class="fas fa-times ms-5"></i></span>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
        <div>
            <FactoryCardWithDTO v-for="factory in factories" :key="factory.id" :factory="factory"/>
        </div> 
    </div>
</template>

<script setup>
    import { ref, onMounted, defineProps } from 'vue';
    import FactoryCardWithDTO from '@/components/FactoryCardWithDTO.vue';
    import axios from 'axios';

    const searchParams = ref(null);
    const factories = ref([]);

    const showFilter = ref(false);
    const showTypeFilter = ref(false);
    const showKindFilter = ref(false);
    const showStatusFilter = ref(false);
    const showSort = ref(false);

    const props = defineProps({
        search: {
            type: String,
            required: true,
            default: null
        }
    });

    function SortNameUp() {
        factories.value.sort((a, b) => {
            return a.name.localeCompare(b.name);
        });
    }

    function SortNameDown() {
        factories.value.sort((a, b) => {
            return b.name.localeCompare(a.name);
        });
    }

    function SortLocationUp() {
        factories.value.sort((a, b) => {
            return a.address.street.localeCompare(b.address.street);
        });
    }

    function SortLocationDown() {
        factories.value.sort((a, b) => {
            return b.address.street.localeCompare(a.address.street);
        });
    }

    function SortRatingUp() {
        factories.value.sort((a, b) => a.rating - b.rating);
    }

    function SortRatingDown(){
        factories.value.sort((a, b) => b.rating - a.rating);
    }

    function setKindFilter(kind){
        searchParams.value.chocolateKind = kind;
        console.log(searchParams.value.chocolateKind);
        search();
    }

    function setTypeFilter(type){
        searchParams.value.chocolateType = type;
        search();
    }

    function setStatusFilter(status){
        searchParams.value.status = status;
        search();
    }

    function removeFilter(filter){
        switch(filter){
            case 'Kind':
                searchParams.value.chocolateKind = '';
                console.log(searchParams)
                break;
            case 'Type':
                searchParams.value.chocolateType = '';
                break;
            case 'Status':
                searchParams.value.status = '';
                break;
            default:
                break;
        }
        search();
    }

    function searchClick(){
        search();
    }

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

    onMounted(async () => {
        searchParams.value = JSON.parse(props.search); // Merge the search object into reactive
        console.log(searchParams.value); // Check the value
        await search();
    });

    async function search(){
        try{
            console.log("--------");
            console.log(JSON.stringify(searchParams.value));
            const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/factory/search', searchParams.value);
            factories.value = response.data;
        }catch (error) {
            console.error('Error loading factories:', error);
        }
        
    }
</script>