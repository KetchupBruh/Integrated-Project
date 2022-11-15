<script setup>
import { ref , onBeforeMount } from 'vue'
import UserDetail from '../components/UserDetail.vue'
import { useRoute } from 'vue-router'
const user = ref([]);
let { params } = useRoute()
const userIdFromBase = params.id
const toSend = ref([]);
const createOnTime = ref("");
const updatedOnTime = ref("");


onBeforeMount(async () => {
  await getUsersId();
  
})

const getUsersId = async () => {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/users/${userIdFromBase}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    user.value = await res.json()
    const time1 = user.value.createOn;
    const time2 = user.value.updatedOn;
    createOnTime.value = time1.substring(0, 16);
    updatedOnTime.value = time2.substring(0, 16);
    return [user.value,createOnTime.value,updatedOnTime.value]
  } else console.log('error, cannot get notes')
}

//Delete
const removeUser = async function (deleteUserId) {
console.log(deleteUserId);
if (confirm("You want to delete this user ?")) {
const res = await fetch(`${import.meta.env.VITE_BASE_URL}/users/${deleteUserId}`, {
method: 'DELETE',
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
});
if (res.status === 200) {
user.value = user.value.filter((user) => user.userId !== deleteUserId);
console.log('deleted user successfully');
alert('deleted user successfully');
} else
console.log('error, cannot delete user');
}
else {
alert('not delete');
}
}

// PUT
const UpdateNewData = async (editUser) => {
    toSend.value = {
    userId: editUser.userId,
    userName: editUser.userName,
    userEmail: editUser.userEmail,
    role: editUser.role,
  };

 console.log(toSend.value);
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/users/${userIdFromBase}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem("token")}`
    },
    body: JSON.stringify( toSend.value )
  });
  if (res.status === 200) {
    alert("Edit User Successfully");
  }
  else {
    alert("Edit User Fail");
    console.log('edit user fail');
  }
}

</script>
 
<template>
    <div class="displayAboutUS">
    <UserDetail :usersList="user" @deleteUser="removeUser" @updateUser="UpdateNewData" :updatedOn="updatedOnTime" :createdOn="createOnTime"></UserDetail>
  
</div>
</template>
 
<style>

</style>
