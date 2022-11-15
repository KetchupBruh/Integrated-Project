<script setup>
import { ref , onBeforeMount } from 'vue'
import EventDetail from '../components/EventDetail.vue'
import { useRoute } from 'vue-router'
const reTime = ref("");
const event = ref([]);
const clinicList = ref("");
let { params } = useRoute()
const eventId = params.id
const toSend = ref([]);


onBeforeMount(async () => {
  await getEventId();
  await getCategory();

})

//get
const getEventId = async () => {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/${eventId}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  });
  if (res.status === 200) {
    event.value = await res.json()
    const time = event.value.eventStartTime;
    reTime.value = time.substring(0, 16);
    return [event.value,reTime.value];
  } else console.log('error, cannot get notes')
}

//Delete
const removeEvent = async (deleteEventId) => {
  console.log(deleteEventId)
  if (confirm("You want to delete this Appointment ?")) {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/${deleteEventId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    }
  })
  if (res.status === 200) {
    event.value = event.value.filter((event) => event.eventId !== deleteEventId)
    console.log('deleted Appointment successfully');
    alert('deleted Appointmentt successfully');
  } else console.log('error, cannot delete event');
  }
  else {
    alert('not delete' );
  }
  await getEvent();
}

// PUT
const UpdateNewData = async (event,time) => {
  toSend.value = {
    eventId:event.eventId,
    bookingName: event.bookingName,
    bookingEmail: event.bookingEmail,
    eventCategoryName: event.eventCategoryName,
    eventStartTime: time+":00Z",
    eventDuration: event.eventDuration,
    eventNotes: event.eventNotes,
    eventCategory_eventCategoryId: JSON.stringify(
      clinicList.value.filter((x) => x.eventCategoryName === event.eventCategoryName)[0].id
    )
    
  };

 console.log(toSend.value);
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/${eventId}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify( toSend.value )
  });
  if (res.status === 200) {
    alert("Edit Appointment Successfully");
  }
  
}
const getCategory = async ()=> {
  
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
    clinicList.value = await res.json()

}

</script>
 
<template>
<div class="displayAboutUS">
  
    <EventDetail :events="event" :categories="clinicList" :timeDate="reTime" @deleteEvent="removeEvent" @updateEvent="UpdateNewData"></EventDetail>
  
</div>
    

</template>
 
<style>

</style>