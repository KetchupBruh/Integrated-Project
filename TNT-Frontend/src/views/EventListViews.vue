<script setup>
import { ref , onBeforeMount } from 'vue'
import EventList from '../components/EventList.vue'
const event = ref([]);
const clinicList = ref("");
const filterCatagories = ref("");
const filterDates = ref("");
const filterEvents = ref("");
const switchButton = ref(true);



 const getFilterCategory = async() => {
    const currentFilterCategory = filterCatagories.value;
    const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/eventCategoryId=${currentFilterCategory}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
    if (res.status === 200) {
      event.value = await res.json()
    } else console.log('error, cannot get notes')
    switchButton.value = false;
}

 const getFilterDate = async() => {
    const pickFilterDate = filterDates.value;
    const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/date/${pickFilterDate}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
    if (res.status === 200) {
      event.value = await res.json()
    } else console.log('error, cannot get notes')
    switchButton.value = false;
}

const getFilterTime = async() => {
  if(filterEvents.value == "Upcoming" ){
    const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/future`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
    if (res.status === 200) {
      event.value = await res.json()
    } else console.log('error, cannot get notes')
  }
  else if(filterEvents.value == "Past"){
    const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/past`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
    if (res.status === 200) {
      event.value = await res.json()
    } else console.log('error, cannot get notes')
  }
  switchButton.value = false;
}

onBeforeMount(async () => {
  await getEvent();
  await getCategory();
  
})

const getEvent = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/events`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    event.value = await res.json()
    return event.value
  } else console.log('error, cannot get notes')
}

const getCategory = async ()=> {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
  if (res.status === 200) {
    clinicList.value = await res.json()
  } else console.log('error, cannot get notes')

}

const reset = () => {
  filterCatagories.value = null;
  filterDates.value = null;
  filterEvents.value = null;
  getEvent();
  switchButton.value = true;
}

</script>
 
<template>
<div class="listColumn">
   
   <div class="column">
     <h2 class="schedule"> --- Schedule Appointment --- </h2>
   <div class="column middle">
    <p v-if="event.length == 0" style="color: red">No Scheduled Events</p>
    <p v-else><EventList :events="event" @deleteEvent="removeEvent"></EventList></p>
  </div>
  </div>
  <div class="column side">
    <h2 class="schedule">Filter Appointment</h2>
    <p>Select Category</p>
    <select v-if="switchButton == true" v-model="filterCatagories" @change="getFilterCategory()" >
        <option v-for="(clinic,index) in clinicList" :key="index" :value="clinic.id">
        {{clinic.eventCategoryName}}
      </option>
    </select>
     <select v-else v-model="filterCatagories" disabled>
        <option v-for="(clinic,index) in clinicList" :key="index" >
        {{clinic.eventCategoryName}}
      </option>
    </select>
    <p>Select Event</p>
    <select v-if="switchButton == true" v-model="filterEvents" @change="getFilterTime()" >
      <option value="Upcoming"  >
        Upcoming
      </option>
      <option value="Past" >
        Past
      </option>
    </select>
        <select v-else v-model="filterEvents" disabled >
      <option value="Upcoming" >
        Upcoming
      </option>
      <option value="Past" >
        Past
      </option>
    </select>
    <p>Select Date</p>
    <input type="date" v-if="switchButton == true" v-model="filterDates" @input ="getFilterDate()">
    <input type="date" v-else v-model="filterDates" disabled>
    <p>Reset</p>
    <button class="button-28" role="button" @click="reset()">Reset</button>

  </div>
</div>

</template>
 
<style>

.schedule{
    color: #edb88b;
    font-size: 32px;
    font-family:"Lexend";
    text-align: center;

}
.listColumn{
  padding : 100px;
  overflow: auto;
  
}
.column {
  margin-top: -20px;
  float: left;
  padding: 25px;
  width: 60%;
  height: 500px;
  background-color: aliceblue;
  border-radius: 20px;
  
}

/* Left and right column */
.column.side {
  margin-left: 2%;
  width: 30%;
  height: 500px;
  background-color: aliceblue;
  border-radius: 20px;
  padding-right: 30px;
}

/* Middle column */
.column.middle {
  width: 90%;
  height: 350px;
  background-color: aliceblue;
  overflow: auto;
 
}
</style>