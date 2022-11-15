<script setup>
import { ref , onBeforeMount } from 'vue'
import UsersList from '../components/UsersList.vue'
const users = ref([]);
const newAccess = ref();
let token = localStorage.getItem("token")
const refreshToken = localStorage.getItem("refreshToken");


onBeforeMount(async () => {
  await getUsers()
})



const getUsers = async () => {

  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/users`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    users.value = await res.json();
    users.value.sort();
  } else if (res.status === 401 && token !== null){
    RefreshToken();
  }
};

const RefreshToken = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/login/refresh-token`,{
      method: 'get',
      headers: {
        Authorization: `Bearer ${refreshToken}`
      }
    }
  );
  if (res.status === 200) {
    newAccess.value = await res.json()
    refresh()
    getUsers()
  } else if (res.status === 401){
    localStorage.clear()
    window.location.reload();
    console.log("please log out");
  }
};

</script>
 
<template>
    <p v-if="users.length == 0" style="color: red"><h1>No Users</h1></p>
    <p v-else><UsersList :usersList="users"></UsersList></p>
</template>
 
<style>

</style>