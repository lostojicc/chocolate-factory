<template>
    <div class="col-lg-6 wow bounceInUp" data-wow-delay="0.1s">
        <div class="border border-primary rounded">
            <div class="menu-item d-flex align-items-center p-3 border-bottom border-primary">
                <img class="flex-shrink-0 img-fluid rounded-circle" src="../img/menu-01.jpg" alt="">
                <div class="w-100 d-flex flex-column text-start ps-4">
                    <div class="d-flex justify-content-between border-bottom border-primary pb-2 mb-2">
                        <h4>{{ chocolate.name }}</h4>
                            <p class="my-1"> ( {{ chocolate.kind }}</p>
                            <p class="my-1">, </p>
                            <p class="my-1">{{ chocolate.type }} )</p>
                            <p class="my-1"></p>
                            <p class="my-1"></p>
                            <p class="my-1"></p>
                            <p class="my-1"></p>
                            <p class="my-1"></p>
                        <h4 class="text-primary">${{ chocolate.price }}</h4>
                        <h4>/ {{ chocolate.grams }}g</h4>
                    </div>
                    <p class="mb-0">{{ chocolate.description }}</p>
                </div>
            </div>    
            <div class="row p-3">
                <div v-if="chocolate.isAvailable == true" class="col-8 d-flex align-items-center">
                    <h4 class="text-primary m-2">Available:</h4>
                    <h4 class="text-dark m-2">{{ chocolate.quantity }}</h4>
                </div>
                <div v-else class="col-8 d-flex align-items-center">
                    <h4 class="text-primary m-2">Not available</h4>
                </div>
                <div class="col d-flex">
                    <a class="btn btn-primary btn-sm-square me-2 rounded-circle" href=""><i class="fas fa-shopping-bag"></i></a>
                    <a class="btn btn-primary btn-sm-square me-2 rounded-circle" href=""><i class="fas fa-pencil-alt"></i></a>
                    <a class="btn btn-primary btn-sm-square me-2 rounded-circle" href=""><i class="fas fa-trash-alt"></i></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps } from 'vue';

    const props = defineProps({
        chocolate: {
            type: Object,
            required: true
        }
    });

    console.log(props.chocolate.name);
</script>