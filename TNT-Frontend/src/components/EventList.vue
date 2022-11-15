<script setup>
import moment from 'moment'

defineProps({
  events: {
    type: Array,
    require: true
  },
  categories: {
    type: Array,
    require: true
  }
})

</script>
 
<template>
<div v-for="(event, index) in events" :key="index">
    <div class="cardH">
    <div class="image">
        <img src="/src/assets/icon.gif">
    </div>
    <div class="text">
        <span class="name">{{event.bookingName}}</span>
        <span class="category">Category : </span>
        <span class="date">{{event.eventCategoryName}} </span>
        <span class="category"><br>Duration : </span>
        <span class="categoryTime">{{event.eventDuration}} Minutes</span>
        
        <span class="category"><br>Date : </span>
        <span class="date">{{moment.utc(event.eventStartTime).format('LL')}}</span>
        <span class="category"><br> Startime : </span>
        <span class="date">{{moment.utc(event.eventStartTime).format('LT')}}</span>
        <br>
        <router-link :to="{ path: '/event-detail-'+ event.eventId}" class="button-18" role="button">Read more ></router-link>
    </div>
    </div>
</div>
</template>
 
<style>

.cardH {
  width: 90%;
  height: auto;
  padding: 18px;
  border: 1px solid #f1f1f1;
  border-radius: 8px;
  
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.02), 0 4px 4px 0 rgba(0, 0, 0, 0.04);
  background-color: #fff;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.cardH .image {
  width: 150px;
  height: 150px;
}

.cardH .image img {
  width: 100%;
  height: 90%;
  border-radius: 50%;
}

.cardH .text {
  margin-left: 18px;
}

.cardH .text .name {
  font-weight: 600;
  font-size: 1.1rem;
  display: block;
}

.cardH .text .category {
  display: inline;
  font-weight: 500;
  color: #999;
  margin-top: 4px;
}

.cardH .text .categoryTime {
  display: inline;
  font-weight: 500;
  color: rgb(78, 141, 197);
  margin-left: 5px;
}

.cardH .text .date {
  display: inline;
  font-weight: 500;
  color: rgb(78, 141, 197);
}


/* CSS */
.button-18 {
  align-items: center;
  background-color: #ff9625;
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto, "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell, "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
  line-height: 20px;
  max-width: 480px;
  min-height: 30px;
  min-width: 0px;
  overflow: hidden;
  padding: 0px;
  padding-left: 20px;
  padding-right: 20px;
  text-align: left;
  touch-action: manipulation;
  transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
  text-decoration: none;
}

.button-18:hover,
.button-18:focus { 
  background-color: #3b1d09;
  color: #ffffff;
}

.button-18:active {
  background: #3b1d09;
  color: rgb(255, 255, 255, .7);
}

.button-18:disabled { 
  cursor: not-allowed;
  background: rgba(0, 0, 0, .08);
  color: rgba(0, 0, 0, .3);
}
</style>
