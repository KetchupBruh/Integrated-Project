<script setup>
defineEmits(['updateEvent'])
const props = defineProps({

  categories: {
    type: Array,
    require: true
  },
  name:{
    type: Array,
    require: true
  },
  switchButton:{
    type: Boolean,
    require: false
  }
})

const isNameValid = () => {
  if(props.name.eventCategoryName == props.categories.eventCategoryName){
    console.log("Not pass");
    return true;
  }
  return false;
}
</script>
 
<template>
    <div class="formdivEdit">
    <fieldset>
      <h1 class="makeAppointment">Category ID : {{categories.id}} <button class="button-53" role="button" @click="switchButton = !switchButton">Edit</button></h1>   

       <label>Name:<span class="canEdit" v-if="switchButton === true">(Can edit but only different name)</span></label>
      <input v-if="switchButton === false" type="text" v-model=categories.eventCategoryName disabled>
      <input v-else type="text" v-model=categories.eventCategoryName @keyup="isNameValid()" max="100" >

      <label>Duration:<span class="canEdit" v-if="switchButton === true">(Can edit 1-480 only)</span></label>
      <input v-if="switchButton === false" type="number" v-model=categories.eventDuration disabled>
      <input v-else type="number" v-model=categories.eventDuration max="480" min="1" >

      <label>Description: <span class="canEdit" v-if="switchButton === true">(Can edit)</span></label>
      <textarea v-if="switchButton === false" maxlength = "500" v-model="categories.eventCategoryDescription" rows="4" cols="50" disabled></textarea>
      <textarea v-else maxlength = "500" v-model="categories.eventCategoryDescription" rows="4" cols="50"></textarea>
    </fieldset>
      
      <button v-show="switchButton === true" class="button-28" role="button" @click="$emit('updateEvent', categories,switchButton = !switchButton)" :disabled ="isNameValid()">Update a Category</button>
    </div>
</template>
 
<style>
    .formdivEdit {
  max-width: 60%;
  margin: 100px auto;
  padding: 10px 25px;
  background: #f4f7f8;
  border-radius: 8px;
  border: 1px solid #8265B0;
  box-shadow: 3px 3px 3px rgba(0,0,0,0.2)
  
}
</style>