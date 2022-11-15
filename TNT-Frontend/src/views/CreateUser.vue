<script setup>
import { ref } from 'vue';
import { computed } from '@vue/reactivity';
import { useRouter } from 'vue-router';
const tosend = ref([]);
const userName = ref("");
const userEmail = ref("");
const userPassword = ref("");
const userPasswordCom = ref("");
const selectRole = ref("");
const wrongEmail = ref(true);
const wrongName = ref(true);
const myRouter = useRouter();
const goThisUser = () => myRouter.push({name: 'UsersList'});

const disButton = computed( () => {
  if(wrongEmail.value == false && wrongName.value == false && isPasswordValid() == true && selectRole.value != ""){
    return true;
  }
  else return false;
})

const clearData = () => {
     userName.value = "";
     userEmail.value = "";
     Password.value = "";
}
const createNewData = async () => {
  tosend.value = {
    userName: userName.value.trim(),
    userEmail: userEmail.value.trim(),
    password: userPassword.value,
    role: selectRole.value,
  };
  if (confirm("You want to Sign up ?")) {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/users/register`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify( tosend.value )
  });
  if (res.status === 201) {
    alert("Sign up Successfully");
    clearData();
    goThisUser();
  }
  else alert("Can't Sign up");}
  else alert("Can't Sign up");
  
}


const emailRe = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const isEmailValid = () => {
      if (emailRe.test(userEmail.value)) {
        wrongEmail.value = false;
      } else {
        wrongEmail.value = true;
      }
    }
  

const isNameValid = () => {
  if(userName.value.length <= 0 && userName.value != null){
    wrongName.value = true;
  }
  else wrongName.value = false;
}

const isPasswordValid = () => {
  if(userPassword.value==userPasswordCom.value && userPassword.value != "" && userPassword.value.length >= 8 && userPassword.value.length <= 14 ){
    return true;
  }
  return false;
}
</script>
 
<template>
  <div class="displayAboutUS">
    <div class="formdiv">
    <fieldset>
       <legend><h1 class="makeAppointment">Create User</h1></legend>
    
      <label>Name :</label>
      <label v-show="wrongName" style="color:red;font-size: 10px;">Characters must not exceed 100.</label>
      <input type="text" v-model="userName" maxlength="100" @keyup="isNameValid()">
      
      <label>E-Mail :</label>
      <label v-show="wrongEmail" style="color:red;font-size: 10px;">Incorrect email address</label>
      <input type="email" v-model="userEmail" @keyup="isEmailValid()">
      

      <label>Password :</label>
      <label v-show="" style="color:red;font-size: 10px;">*</label>
      <input type="password" v-model="userPassword" maxlength="100" @keyup="isPasswordValid()" placeholder="Password must between 8-14 character">


      <label>Confirm Password :</label>
      <label v-show="" style="color:red;font-size: 10px;">*</label>
      <input type="password" v-model="userPasswordCom" maxlength="100" @keyup="isPasswordValid()">

      
      <label>Role :</label>
      <select v-model="selectRole">
      <option value="admin">admin</option>
      <option value="lecturer">lecturer</option>
      <option value="student">student</option>
      </select>


    </fieldset>
    
      <button class="button-81" role="button" @click="createNewData();" v-show="disButton" >sign up</button>
    
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