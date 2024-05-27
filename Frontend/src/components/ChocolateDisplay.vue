<template>
    <div class="container-fluid menu py-6">
        <div class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Our Menu</small>
                <h1 class="display-5 mb-5">Best Chocolates in the World</h1>
            </div>
            <div class="tab-class text-center">
            <div class="tab-content">
                <div id="tab-6" class="tab-pane fade show p-0 active">
                    <div class="row g-4">
                        <ChocolateCard/>
                        <ChocolateCard/>
                        <ChocolateCard/>
                        <ChocolateCard/>
                        <ChocolateCard/>
                    </div>
                </div>
            </div>
        </div>
        </div>
            
    </div>
</template>

<script setup>
    import ChocolateCard from './ChocolateCard.vue';
</script>