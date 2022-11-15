<script setup>
defineProps({
  usersList: {
    type: Array,
    require: true
  }
})

</script>
 
<template>
<div class="displayAboutUS">
<h3 class="aboutUs">User</h3>
    <div class="container">
        <div class="item" v-for="(user, index) in usersList" :key="index">
            <div>
            <h3 style="color: #ff7426;">{{user.userName}}</h3>
            <h4>Email : {{user.userEmail}}</h4>
            <h4>Role : {{user.role}}</h4>
            <router-link :to="{ path: '/users-detail-'+ user.userId}" class="button-18" role="button">View</router-link>
            </div>
        </div>
    </div>
  </div>
</template>
 
<style>

</style>