<template>
    <div class="container-fluid py-6">
            <div class="container">
                <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Comments</small>
                    <h1 class="display-5 mb-5">What Our Customers say!</h1>
                </div>
                <CommentCard/>
            </div>
        </div>
</template>

<script setup>
    import CommentCard from '../components/CommentCard.vue';
</script>