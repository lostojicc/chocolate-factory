<template>
    <div class="container-fluid py-6">
        <div v-if="canComment" class="container">
            <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Comments</small>
                    <h1 class="display-5 mb-5">Leave a comment!</h1>
            </div>
            <div class="comment-container d-flex flex-column justify-content-around align-items-center">
                <textarea v-model="comment.text" class="w-100 form-control mb-4 p-3 border-primary bg-light" rows="4" cols="10" placeholder="Enter your comment" @input="textChanged()"></textarea>
                <span v-if="showCommentValidation" class="red mb-3">Please write a comment before submitting</span>
                <span v-if="showGradeValidation" class="red mb-3">Please select a grade before submitting</span>
                <div class="grade-buttons">
                    <span class="mx-4">Grade: </span>
                    <button class="btn border-primary btn-lg-square me-2 rounded-circle" v-for="grade in grades" :key="grade" @click="setGrade(grade)"
                    :class="{ 'btn-primary': comment.grade === grade, 'btn-light' : comment.grade !== grade}">
                    <h3 class="text-dark">{{ grade }}</h3>
                </button>
                </div>
                <button type="submit" class="mt-3 d-flex align-items-center justify-content-start btn btn-primary py-3 px-4 rounded-pill fixed-size-button" 
                v-on:click="SubmitClick()">
                    <i class="fas fa-check fa-lg px-2"></i>
                    Submit
                </button>
            </div>
        </div>
        <div class="container py-6">
                <div class="text-center wow bounceInUp" data-wow-delay="0.1s">
                    <h1 class="display-5 mb-5">What Our Customers say!</h1>
                </div>
                <CommentCard v-for="comment in comments" :comment="comment" @loadEvent="loadComments()" :editable="editable"/>
        </div>
    </div>
</template>

<script setup>
    import CommentCard from '../components/CommentCard.vue';
    import { defineProps } from 'vue';
    import {ref, onMounted } from 'vue';
    import axios from 'axios';

    const props = defineProps({
        factory: {
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

    const comments = ref([]);
    const canComment = ref(false)
    const grades = ref([1, 2, 3, 4, 5])

    const showCommentValidation = ref(false)
    const showGradeValidation = ref(false)

    const comment = ref({
        grade: 0,
        text: '',
        state: 'Pending',
        factoryId: props.factory.id,
        userId: 0
    })

    onMounted(() => {
        load()
    });

    async function load(){
        await loadComments();
        await CheckCanBeCommented();
    }

    async function loadComments(){
        if(userRole !== 'Manager' && userRole !== 'Administrator'){
            acceptedComments()
        }
        else{
            allComments()
        }
    }

    async function CheckCanBeCommented(){
        if(userRole !== 'Customer'){
            return;
        }

        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/canUserComment/${username}/${props.factory.id}`,
            {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }}
            );
            if (response.status === 200) {
                canComment.value = true;
            }
        } catch (error) {
            canComment.value = false;
        }
    }

    async function acceptedComments(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/acceptedComments/${props.factory.id}`);
            comments.value = response.data;
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }

    async function allComments(){
        try {
            const response = await axios.get(`http://localhost:8080/WebShopAppREST/rest/factory/comments/${props.factory.id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            comments.value = response.data;
            sortComments()
        } catch (error) {
            console.error('Error loading comments:', error);
        }
    }

    function sortComments() {
        comments.value.sort((a, b) => {
            const stateOrder = { Pending: 1, Accepted: 2, Rejected: 3 };
            return stateOrder[a.state] - stateOrder[b.state];
        });
    }

    function setGrade(grade){
        showGradeValidation.value = false;
        comment.value.grade = grade;
    }

    function textChanged(){
        showCommentValidation.value = false;
    }

    async function SubmitClick(){
        let logic = true
        if(comment.value.grade === 0){
            showGradeValidation.value = true
            logic = false
        }

        if(comment.value.text === ''){
            showCommentValidation.value = true
            logic = false
        }

        if(!logic)
            return

        try {
            const response = await axios.post(`http://localhost:8080/WebShopAppREST/rest/factory/comments/addComment/${username}`, comment.value, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
                }});
            load()
        } catch (error) {
            console.error('Error loading comments:', error);
        }

    }

</script>

<style scoped>
.red{
    color: red;
}
</style>
