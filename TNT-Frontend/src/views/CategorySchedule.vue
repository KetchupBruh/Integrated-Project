<script setup>
import { ref , onBeforeMount } from 'vue'
import CatagoryList from '../components/CategoryList.vue'
const categories = ref([]);

onBeforeMount(async () => {
  await getCatagorys()
  
})

const getCatagorys = async () => {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/eventcategories`, 
  {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  if (res.status === 200) {
    categories.value = await res.json()
    return categories.value
  } else console.log('error, cannot get notes')
}

</script>
 
<template>
    <CatagoryList :categoriesList="categories"></CatagoryList>
</template>
 
<style>

</style>