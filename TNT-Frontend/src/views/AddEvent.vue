<script setup>
import { onBeforeMount, ref } from 'vue';
import moment from 'moment'
import { computed } from '@vue/reactivity';
const clinicList = ref("");
const tosend = ref([]);
const bookingName = ref("");
const bookingEmail = ref("");
const bookingCatagory = ref("Project Management Clinic");
const bookingTime = ref("");
const note = ref("");
const wrongEmail = ref(true);
const wrongTime = ref(true);
const wrongName = ref(true);
const now = new Date();
const reCurrentTime = moment(now).format();
const currentTime = reCurrentTime.substring(0, 16);
const token = localStorage.getItem("token");

const disButton = computed( () => {
  if(wrongTime.value == false && wrongName.value == false){
    return true;
  }
  else return false;
})

const parseJwt = (token) => {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  }

onBeforeMount(async () => {
  await getCategory()
  getBookingEmail()
})
const getBookingEmail = () => {
  if(token!=null){
    return bookingEmail.value = parseJwt(localStorage.getItem("token")).sub;
  }
  else{
    return bookingEmail.value = null;
  }
}

const clearData = () => {
     bookingName.value = "";
     bookingEmail.value = "";
     bookingCatagory.value = "Project Management Clinic";
     bookingTime.value = "";
     note.value = "";
}
const createNewData = async () => {
  tosend.value = {
    bookingName: bookingName.value,
    bookingEmail: bookingEmail.value,
    eventCategory: bookingCatagory.value,
    eventStartTime: bookingTime.value+":00Z",
    eventDuration: JSON.stringify(
      clinicList.value.filter((x) => x.eventCategoryName === bookingCatagory.value)[0].eventDuration
    ),
    eventNotes: note.value,
    eventCategory_eventCategoryId: JSON.stringify(
      clinicList.value.filter((x) => x.eventCategoryName === bookingCatagory.value)[0].id
    )
    
  };
  if (confirm("You want to Make Appointment ?")) {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/events/`, {
    method: 'POST'
    ,
    headers: {
      // Authorization: `Bearer ${localStorage.getItem("token")}`,
      'content-type': 'application/json'
    }
    ,
    body: JSON.stringify( tosend.value )
  });
  if (res.status === 201) {
    alert("Make Appointment Successfully");
    clearData();
  }
  else alert("Can't Make Appointment");}
  else alert("Can't Make Appointment");
}

const getCategory = async ()=> {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories`
  , 
  {
    method: "GET"
    // ,
    // headers: {
    //   Authorization: `Bearer ${localStorage.getItem("token")}`,
    // }
  }
  );
  if (res.status === 200) {
    clinicList.value = await res.json()
  } else console.log('error, cannot get notes')
}

const emailRe = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const isEmailValid = () => {
      if (emailRe.test(bookingEmail.value)) {
        wrongEmail.value = false;
      } else {
        wrongEmail.value = true;
      }
    }
  
const isTimeValid = () => {
  const lockPickTime = moment(bookingTime.value).format();
  const lockCurrentTime = moment(now).format();
  if (moment(lockCurrentTime).isAfter(moment(lockPickTime))) {
    wrongTime.value = true;
}
else wrongTime.value = false
}

const isNameValid = () => {
  if(bookingName.value.length <= 0 && bookingName.value != null){
    wrongName.value = true;
  }
  else wrongName.value = false;
}

</script>
 
<template>
  <div class="displayAboutUS">
    <div class="formdiv">
    <fieldset>
       <legend><h1 class="makeAppointment">Make Appointment</h1></legend>
      <label>Catagory : </label>
      <select v-model="bookingCatagory">
        <option v-for="(clinic,index) in clinicList" :key="index" >
        {{clinic.eventCategoryName}}
      </option>
      </select>

      <label>Name:</label>
      <label v-show="wrongName" style="color:red;font-size: 10px;">Incorrect Name</label>
      <input type="text" v-model="bookingName" maxlength="100" @keyup="isNameValid()">

      <label>E-Mail:</label>
      <label v-show="wrongEmail" v-if="token == null" style="color:red;font-size: 10px;">Incorrect email address</label>
      <input v-if="token != null" type="email" v-model="bookingEmail" disabled>
      <input v-else type="email" v-model="bookingEmail" @keyup="isEmailValid()">
  
      <label>Time:</label>
      <label v-show="wrongTime" style="color:red;font-size: 10px;">please input after current time</label>
      <input class='form-control' type='datetime-local' v-model="bookingTime" @input ="isTimeValid()" :min="currentTime">
  
      <label>Note:</label>
      <textarea maxlength = "500" v-model="note"></textarea>
    </fieldset>
    
      <button class="button-81" role="button" @click="createNewData();" v-show="disButton" >Appointment</button>
    
    </div>
    
  </div>
</template>
 
<style>

  .formdiv {
  max-width: 60%;
  margin: 20px auto;
  padding: 10px 25px;
  background: #f4f7f8;
  border-radius: 8px;
  border: 1px solid #8265B0;
  box-shadow: 3px 3px 3px rgba(0,0,0,0.2)
  
}


input[type="text"],
input[type="password"],
input[type="date"],
input[type="datetime-local"],
input[type="email"],
input[type="number"],
input[type="search"],
input[type="tel"],
input[type="time"],
input[type="url"],
textarea,
select {
  background: rgba(255,255,255,0.1);
  border: none;
  font-size: 16px;
  height: auto;
  margin: 0;
  outline: 0;
  padding: 15px;
  width: 90%;
  background-color: #e8eeef;
  color: #8a97a0;
  box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
  margin-bottom: 2px;
}

fieldset {
  margin-bottom: 5px;
  border: none;
}

legend {
  font-size: 1.4em;
  margin-bottom: 10px;
}

label {
  display: block;
  margin-bottom: 8px;
}

label.light {
  font-weight: 300;
  display: inline;
}

@media screen and (min-width: 480px) {

  form {
    max-width: 480px;
  }

}

/* CSS */
.button-81 {
  background-color: #1e293b;
  border: 0 solid #e2e8f0;
  border-radius: 1.5rem;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  font-family: "Basier circle",-apple-system,system-ui,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
  font-size: 1.1rem;
  font-weight: 600;
  line-height: 1;
  padding: 1rem 1.6rem;
  text-align: center;
  text-decoration: none #0d172a solid;
  text-decoration-thickness: auto;
  transition: all .1s cubic-bezier(.4, 0, .2, 1);
  box-shadow: 0px 1px 2px rgba(166, 175, 195, 0.25);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-81:hover {
  background-color: #edb88b;
  color: #1e293b;
}

@media (min-width: 768px) {
  .button-81 {
    font-size: 1.125rem;
    padding: 1rem 2rem;
  }
}

.makeAppointment{
    color: #edb88b;
    font-size: 32px;
    font-family:"Lexend";
    text-align: left;
    margin-right: 0;
}


</style>