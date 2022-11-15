<script setup>
  const token = localStorage.getItem("token");
  const signOut = () => {
    localStorage.clear()
    window.location.reload();
  }

</script>

<template>
<div>

    <div class="navbar">
    <div class="logo">
      <h1>TNT Clinic</h1>
    </div>

    <div class="nav__links">
      <router-link :to="{ name: 'Home' }" class="nav__link">Home</router-link>
      <router-link :to="{ name: 'CategoryScheduel' }" class="nav__link">Category</router-link>
      <router-link :to="{ name: 'EventList' }" class="nav__link" >Check Schedule</router-link>
      <router-link :to="{ name: 'MakeAppointment' }" class="nav__link">Make Appointment</router-link>
       <router-link :to="{ name: 'UsersList' }" class="nav__link">Users</router-link>
       <router-link :to="{ name: 'CreateUser' }" class="nav__link">Sign up</router-link>
       <div v-show="token == null">
        <router-link :to="{ name: 'SignIn' }" class="nav__link">Sign in</router-link>
      </div>
      <router-link :to="{ name: 'About' }" class="nav__link">About us</router-link>
      <div v-show="token !== null">
            <p class="nav__link" @click="signOut()">Sign Out</p>
      </div>
    </div>
    
  </div>

</div>
  <!-- <div>
    <div id='stars'></div>
    <div id='stars2'></div>
    <div id='stars3'></div>
  </div> -->
  <div>
    <router-view />
  </div>
  
</template>

<style scoped>
  @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@500&display=swap");

* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
  
}

.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 50px;
  top: 0;
  left: 0;
  box-shadow: 0 1px 5px 1px rgba(0, 0, 0, 0.2);
  width: 100vw;
  position: fixed;
  /* background-color: #282828; */
  transition: all 0.5s ease-in-out;
}

.logo h1 {
  cursor: pointer;
  color: aliceblue;
}

.nav__links {
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav__link {
  margin: 0 10px;
  color: aliceblue;
  padding: 5px 15px;
  border-radius: 100px;
  transition: all 0.5s ease-in-out;
  text-decoration: none;
}

.nav__link.active,
.nav__link:hover {
  background-color: black;
  color: white;
}

.section {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.section:nth-child(odd) {
  background-color: rgb(235, 235, 235);
}
</style>
