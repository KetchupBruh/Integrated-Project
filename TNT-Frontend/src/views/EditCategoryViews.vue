<script setup>
import { ref , onBeforeMount } from 'vue'
import EditCategoryDetail from '../components/EditCategory.vue'
import { useRoute } from 'vue-router'
const clinicList = ref("");
const categoryName = ref ("");
let { params } = useRoute()
const eventCategoryId = params.id
const toSend = ref([]);

onBeforeMount(async () => {
  await getCategory()
})

const getCategory = async ()=> {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories/${eventCategoryId}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    clinicList.value = await res.json()
  } else console.log('error, cannot get notes')
  getCategoryNameList();
}

const getCategoryNameList = async () => {
 const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories/${eventCategoryId}`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    categoryName.value = await res.json()
  } else console.log('error, cannot get notes')

}

const UpdateNewData = async (categorys) => {
  console.log(categorys);
  toSend.value = {
      id: categorys.id,
      eventCategoryName: categorys.eventCategoryName,
      eventCategoryDescription: categorys.eventCategoryDescription,
      eventDuration: categorys.eventDuration
  };
 if (confirm("You want to edit this Category ?")) {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories/${eventCategoryId}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify( toSend.value )
  });
  if (res.status === 200) {
    alert("Edit Category Successfully");
  }
  else alert("Edit Category Fail");
 }
  else alert("Edit Category Fail");
  }

</script>
 
<template>
    <EditCategoryDetail :categories="clinicList" :name="categoryName" @updateEvent="UpdateNewData" switchButton = !switchButton ></EditCategoryDetail>
      <center>
       <button class="button-81" role="button" @click="$router.back()">Go Back</button>
      </center>
</template>
 
<style>

</style>