<script setup>
    import { ref } from 'vue';
    import { computed } from '@vue/reactivity';
    import { useRouter } from 'vue-router';
    const tosend = ref([]);
    const userEmail = ref("");
    const userPassword = ref("");
    const wrongEmail = ref(true);
    const myRouter = useRouter();
    const token = ref()
    const goThisUser = () => {myRouter.push({name: 'UsersList'});};
    const clearData = () => {
         userEmail.value = "";
         userPassword.value = "";
    }
    const saveLocal = () => {
      localStorage.setItem('token',`${token.value.accessToken}`)
      localStorage.setItem('refreshToken',`${token.value.refreshToken}`)
    }
    const createNewData = async () => {
      tosend.value = {
        userEmail: userEmail.value.trim(),
        password: userPassword.value
      };
      if (confirm("You want to Sign in ?")) {
      const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/login`, {
        method: 'POST',
        headers: {
          'content-type': 'application/json'
        },
        body: JSON.stringify( tosend.value )
      });
      if (res.status === 200) {
        alert("Sign in Successfully");
        token.value = await res.json();
        console.log(token.value);
        saveLocal();
        clearData();
        goThisUser();

      }
      else if (res.status === 404) {
        alert("Don't have this email");
        clearData();
        goThisUser();
      }
      else if (res.status === 401) {
        alert("Password is NOT valid");
        clearData();
        goThisUser();
      }
      else alert("Can't Sign in");}
      
    }
    
    const emailRe = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const isEmailValid = () => {
          if (emailRe.test(userEmail.value)) {
            wrongEmail.value = false;
          } else {
            wrongEmail.value = true;
          }
        }
    

    </script>
     
    <template>
      <div class="displayAboutUS">
        <div class="formdiv">
        <fieldset>
           <legend><h1 class="makeAppointment">Sign In</h1></legend>
        
    
          <label>E-Mail :</label>
          <label v-show="wrongEmail" style="color:red;font-size: 10px;">Incorrect email address</label>
          <input type="email" v-model="userEmail" @keyup="isEmailValid()">
          
    
          <label>Password :</label>
          <input type="password" v-model="userPassword" minlength="8" maxlength="14" placeholder="Enter your password">
          <p>Don't have an account? <router-link :to="{ name: 'CreateUser' }" class="nav__link">Sign up</router-link></p>

        </fieldset>
        
        <button class="button-81" role="button" @click="createNewData();" >Sign in</button>
        
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