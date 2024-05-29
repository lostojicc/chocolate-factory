<template>
    <div class="col-lg-6 wow bounceInUp" data-wow-delay="0.1s">
        <div class="border border-primary rounded">
            <div class="menu-item d-flex align-items-center p-3 border-bottom border-primary">
                <img class="flex-shrink-0 img-fluid rounded-circle image-klasick" :src="chocolate.imagePath" alt="">
                <div class="w-100 d-flex flex-column text-start ps-4">
                    <div class="row justify-content-between border-bottom border-primary pb-2 mb-2">
                        <div class="col-md-8 d-flex flex-column">
                            <h4>{{ chocolate.name }}</h4>
                        <div>
                            ( {{ chocolate.kind }}, {{ chocolate.type }} )
                        </div>
                        </div>
                        
                        <div class="col-md-4">
                            <h4 class="text-primary">${{ chocolate.price }}<span class="text-dark">/ {{ chocolate.grams }}g</span></h4>
                        </div>
                    </div>
                    <p class="mb-0">{{ chocolate.description }}</p>
                </div>
            </div>    
            <div class="row p-3">
                <div v-if="chocolate.isAvailable == true" class="col-9 d-flex align-items-center">
                    <h4 class="text-primary m-2">Available:</h4>
                    <h4 class="text-dark m-2">{{ chocolate.quantity }}</h4>
                </div>
                <div v-else class="col-9 d-flex align-items-center">
                    <h4 class="text-dark m-2">Not available</h4>
                </div>
                <div class="col d-flex">
                    <a v-if="chocolate.isAvailable" class="btn btn-primary btn-sm-square me-2 rounded-circle" href=""><i class="fas fa-shopping-bag"></i></a>
                    <a class="btn btn-primary btn-sm-square me-2 rounded-circle" @click="editClick()"><i class="fas fa-pencil-alt"></i></a>
                    <a class="btn btn-primary btn-sm-square rounded-circle" href=""><i class="fas fa-trash-alt"></i></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps } from 'vue';
    import { defineEmits } from 'vue';

    const emit = defineEmits(['editEvent']);

    const props = defineProps({
        chocolate: {
            type: Object,
            required: true
        }
    });

    function editClick(){
        emit('editEvent', props.chocolate);
    }

</script>

<style scoped>
.image-klasick{
    height: 100px; 
    width: 100px;
    object-fit: cover;
}
</style>