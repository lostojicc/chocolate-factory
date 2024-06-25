<template>
    <div class="container-fluid menu py-4">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Your Shopping Cart</small>
            </div>
            <div class="tab-class text-center">
                <div class="tab-content">
                    <div id="tab-6" class="tab-pane fade show p-0 active">
                        <div class="row g-4">
                          <ChocholateItemCard v-for="chocolate in chocolates" :chocolate="chocolate" @editEvent="handleEditEvent" @deleteEvent="handleDeleteEvent"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Fixed Bottom Buttons -->
    <div class="fixed-bottom bg-light">
        <div class="container text-center">
          <div class="row">
            <div class="col">
              <h3 class="mb-4">Total price: {{}}$</h3>
            </div>
            <div class="w-100"></div>
            <div class="col">
              <button type="submit" class="btn btn-primary btn-lg mx-5 mb-5 rounded-pill" v-on:click="SubmitButton">Clear Cart</button>
              <button type="submit" class="btn btn-primary btn-lg mx-5 mb-5 rounded-pill" v-on:click="SubmitButton">Checkout</button>
            </div>
          </div>
        </div>
    </div>
</template>



<script setup>
import { ref, onMounted } from 'vue';
import ChocholateItemCard from './ChocholateItemCard.vue';
import axios from 'axios';



</script>

