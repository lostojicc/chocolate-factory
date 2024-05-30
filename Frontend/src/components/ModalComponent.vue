<!-- ModalComponent.vue -->
<template>
    <div class="modal" v-if="isVisible">
      <div class="modal-content">
        <p>Are you sure you want to continue?</p>
        <button @click="confirmAction">Yes</button>
        <button @click="cancelAction">No</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, defineEmits } from 'vue';
  
  const props = defineProps({
    isVisible: Boolean
  });
  
  const emit = defineEmits(['confirm', 'cancel']);
  
  const confirmAction = () => {
    emit('confirm');
  };
  
  const cancelAction = () => {
    emit('cancel');
  };
  </script>
  
  <style scoped>
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    backdrop-filter: blur(5px);
    z-index: 1000;
  }
  .modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
    z-index: 1001;
  }
  button {
    margin: 10px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }
  </style>
  