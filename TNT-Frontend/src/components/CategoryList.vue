<script setup>
defineProps({
  categoriesList: {
    type: Array,
    require: true
  }
})
</script>
 
<template>
<div class="displayAboutUS">
<h3 class="aboutUs">Category</h3>
    <div class="container">
        <div class="item" v-for="(category, index) in categoriesList" :key="index">
            <div>
            <h3 style="color: #ff7426;">{{category.eventCategoryName}}</h3>
            <h4>Duration : {{category.eventDuration}} Minutes</h4>
            <p v-if="category.eventCategoryDescription == null" style="color: red">No Detail</p>
            <p v-else>{{category.eventCategoryDescription}}</p>
            <router-link :to="{ path: '/edit-category-'+ category.id}" class="button-18" role="button">Edit</router-link>
            </div>
        </div>
    </div>
  </div>
</template> 
 
<style>
   .container {
  overflow: auto;
  margin: 5% auto;
  max-width: 90%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 2em;
  text-align: center;
  flex-wrap: wrap;
  max-height: 450px;
}

.item {
  color: aliceblue;
  display: flex;
  padding: 10px;
  flex-direction: column;
  align-items: center;
  border-radius: .5em;
  width: 70%;
  height: 100%;
  -webkit-box-shadow: 0px 29px 38px -15px rgba(0,0,0,0.43);
  -moz-box-shadow: 0px 29px 38px -15px rgba(0,0,0,0.43);
  box-shadow: 0px 29px 38px -15px rgba(0,0,0,0.43);
  background-color: #282828;
  
  
}


@media(max-width:840px) {
  .container {
    flex-direction: column;
    max-width: 100%;
    
  }
  
  .item {
    flex-direction: row;
    width: 100%;
  }
 
}
::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #282828;
}
</style>