<script setup></script>

<template>
  <div class="displayAboutUS">
    
    <h3 class="aboutUs">About Us</h3>
    <h2 style="color:aliceblue; text-align: center;">OASIP SY4 (TNT Team)</h2>
    
    
    <div class="profile-container">
      <div class="profile-card">
        <img src="/src/assets/nice.png" alt="image1" class="profile-icon" />
        <div class="profile-name">Pornticha Sangmanee</div>
        <div class="profile-position">63130500084</div>
        <div class="profile-position">AKA : Nice</div>
        <a href="https://github.com/Porntichanice" target="_blank" class="button">GitHub</a>
      </div>
      <div class="profile-card">
        <img src="/src/assets/st.png" alt="image2" class="profile-icon" />
        <div class="profile-name">Sutee Vapeetam</div>
        <div class="profile-position">63130500122</div>
        <div class="profile-position">AKA : Saint (T)</div>
        <a href="https://github.com/sttrex-13" target="_blank" class="button">GitHub</a>
      </div>
      <div class="profile-card">
        <img src="/src/assets/tea.png" alt="image3" class="profile-icon" />
        <div class="profile-name">Kittayod Wongwuttinun</div>
        <div class="profile-position">63130500148</div>
        <div class="profile-position">AKA : KetchupBruh (Tae)</div>
        <a href="https://github.com/KetchupBruh" target="_blank" class="button">GitHub</a>
      </div>
    </div>
    <div style="padding: 20px">
      <center>
       <button class="button-81" role="button" @click="$router.back()">Go Back</button>
      </center>
    </div>
   
  </div>
</template>

<style>

.displayAboutUS{
   padding: 50px;
   margin-left: 50px;
}
.aboutUs{
  color: #edb88b;
  font-size: 52px;
  font-family:"Lexend";
  text-shadow: 2px 2px 4px #000000;
  text-align: center;
}
.profile-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 10;
    margin: 0 30px;
}

.profile-card {
    flex: 1 1 30%;
    max-width: 300px;
    background: rgb(28, 28, 28);
    padding: 20px 60px;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 4px;
    box-shadow: 0 0 2px 0 #ccc;
    transition: .3s;
}

.profile-card:hover {
    transform: scale(1.1);
    box-shadow: 0 0 25px -5px #ccc;
}

.profile-icon {
    height: 100px;
    width: 100px;
    object-fit: cover;
    border-radius: 50%;
}

.profile-name {
    font-size: 20px;
    font-weight: bold;
    color: aliceblue;
    margin: 25px 0 10px 0;
}

.profile-position {
    font-size: 14px;
    color: #777;
}

.button {
    margin: 40px 0 0 0;
    padding: 10px 30px;
    color: #999;
    text-decoration: none;
    border: 1px solid #999;
    border-radius: 4px;
}

.button:hover {
    color: #fff;
    background: #748dff;
    border: 1px solid #748dff;
}

@media screen and (max-width: 1000px) {
    .profile-name {
        font-size: 18px;
    }
}

@media screen and (max-width: 800px) {
    .profile-card {
        padding: 20px 15px;
    }

    .profile-name {
        font-size: 16px;
    }
}
</style>
