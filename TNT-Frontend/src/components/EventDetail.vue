<script setup>

import { ref } from 'vue'
import moment from 'moment'
defineEmits(['deleteEvent','updateEvent'])
const props = defineProps({
  events: {
    type: Array,
    require: true
  },
  categories: {
    type: Array,
    require: true
  },
  timeDate: {
    type: Array,
    require: true
  },
  switchButton:{
    type: Boolean,
    require: true
  }
})
const timeEdit = ref();
const now = new Date();
const reCurrentTime = moment(now).format();
const currentTime = reCurrentTime.substring(0, 16);
</script>
 
<template>
<div class="formdiv">
    <fieldset>
      <h1 class="makeAppointment">Bookind ID : {{events.eventId}} <button class="button-53" role="button" @click="switchButton = !switchButton">Edit</button></h1>
      
      <label>Catagory :</label>
      <select v-model="events.eventCategoryName" disabled>
        <option v-for="(clinic,index) in categories" :key="index" >
        {{clinic.eventCategoryName}}
      </option>
      </select>

      <label>Name:</label>
      <input type="text" v-model="events.bookingName" disabled>

      <label>E-Mail:</label>
      <input type="email" v-model="events.bookingEmail" disabled>

      <label>Time:<span class="canEdit" v-if="switchButton === true">(Can edit)</span></label>
      <input v-if="switchButton === false" class='form-control' type='datetime-local' v-model="timeDate" disabled>
      <input v-else class='form-control' type='datetime-local' v-model="timeEdit" :min="currentTime">


      <label>Note: <span class="canEdit" v-if="switchButton === true">(Can edit)</span></label>
      <textarea v-if="switchButton === false" maxlength = "500" v-model="events.eventNotes" disabled></textarea>
      <textarea v-else maxlength = "500" v-model="events.eventNotes" ></textarea>
    </fieldset>
    

      <button v-if="switchButton === false" class="button-28" role="button" @click="$emit('deleteEvent', events.eventId),$router.push('/events-schedule')">Cancel an appointment</button>
      <button v-else class="button-28" role="button" @click="$emit('updateEvent', events,timeEdit,switchButton = !switchButton)">Update an appointment</button>
    </div>
</template>
 
<style>
.canEdit{
  color: #ff6060;
  float: right;
  font-size: 10px;
  margin-top: 10px;
}
/* CSS */
.button-28 {
  appearance: none;
  background-color: transparent;
  border: 2px solid #ff6060;
  border-radius: 15px;
  box-sizing: border-box;
  color: #ff6060;
  cursor: pointer;
  display: inline;
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  margin: 0;
  height: 4%;
  /* min-height: 20px; */
  min-width: 0;
  outline: none;
  padding: 15px;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(.23, 1, 0.32, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: 100%;
  will-change: transform;
}

.button-28:disabled {
  pointer-events: none;
}

.button-28:hover {
  color: #fff;
  background-color: #ff6060;
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.button-28:active {
  box-shadow: none;
  transform: translateY(0);
}

/* CSS */
.button-53 {
  background-color: #edb88b;
  border: 0 solid rgb(78, 141, 197);
  box-sizing: border-box;
  color: aliceblue;
  display: flex;
  font-family: ui-sans-serif,system-ui,-apple-system,system-ui,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
  font-size: 1rem;
  font-weight: 700;
  justify-content: center;
  line-height: 1.75rem;
  padding: .75rem 1.65rem;
  position: relative;
  text-align: center;
  text-decoration: none #000000 solid;
  text-decoration-thickness: auto;
  width: 10%;
  max-width: 460px;
  position: relative;
  cursor: pointer;
  transform: rotate(-2deg);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  float: right;
}

.button-53:focus {
  outline: 0;
}

.button-53:after {
  content: '';
  position: absolute;
  border: 1px solid #000000;
  bottom: 4px;
  left: 4px;
  width: calc(100% - 1px);
  height: calc(100% - 1px);
}

.button-53:hover:after {
  bottom: 2px;
  left: 2px;
}

@media (min-width: 768px) {
  .button-53 {
    padding: .75rem 3rem;
    font-size: 1.25rem;
  }
}
</style>