<script setup>
import moment from 'moment'
defineEmits(['deleteUser','updateUser'])
const props = defineProps({
  usersList: {
    type: Array,
    require: true
  },
  switchButton:{
    type: Boolean,
    require: true
  },
    createdOn: {
    type: Array,
    require: true
  },
    updatedOn: {
    type: Array,
    require: true
  }
})


</script>
 
<template>
    <div class="formdiv">
    <fieldset>
      <h1 class="makeAppointment">User  ID : {{usersList.userId}} <button class="button-53" role="button" @click="switchButton = !switchButton">Edit</button></h1>
    

      <label>User Name:</label>
      <input v-if="switchButton === false" type="text" v-model="usersList.userName" disabled>
      <input v-else type="text" v-model="usersList.userName">

      <label>E-Mail:</label>
      <input v-if="switchButton === false" type="email" v-model="usersList.userEmail" disabled>
      <input v-else type="text" v-model="usersList.userEmail">

      <label>Role:</label>
      <input v-show="switchButton === false" type="text" v-model="usersList.role" disabled>
      <select v-show="switchButton === true" v-model="selectRole">
      <option value="admin">admin</option>
      <option value="lecturer">lecturer</option>
      <option value="student">student</option>
      </select>

      <label>Created On:</label>
      <center><span class="date">{{moment.utc(usersList.createdOn).format('LLL')}}</span></center>


      <label>Updated On:</label>
      <center><span class="date">{{moment.utc(usersList.updatedOn).format('LLL')}}</span></center>

     
     <button v-if="switchButton === false" class="button-28" role="button" @click="$emit('deleteUser', usersList.userId),$router.push('/users-schedule')">delete User</button>
     <button v-else class="button-28" role="button" @click="$emit('updateUser', usersList,switchButton = !switchButton)">Update User</button>
    </fieldset>
    

    </div>
</template>
 
<style>

</style>