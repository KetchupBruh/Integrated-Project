import { createRouter, createWebHistory } from 'vue-router'
import AboutUs from '../views/AboutUs.vue'
import Home from '../views/Home.vue'
import NotFound from '../views/NotFound.vue'
import EventList from '../views/EventListViews.vue'
import EventDetail from '../views/EventDetailViews.vue'
import MakeAppointment from '../views/AddEvent.vue'
import CategorySchedule from '../views/CategorySchedule.vue'
import EditCategory from '../views/EditCategoryViews.vue'
import UsersList from '../views/UsersSchedule.vue'
import UserDetail from '../views/UsersDetailViews.vue'
import CreateUser from '../views/CreateUser.vue'
import SignIn from '../views/SignIn.vue'
const history = createWebHistory('/sy4/')
const routes = [
  {
    path: '/about-us',
    name: 'About',
    component: AboutUs
  },
  {
    path: '/events-schedule',
    name: 'EventList',
    component: EventList
  },
  {
    path: '/categories-schedule',
    name: 'CategoryScheduel',
    component: CategorySchedule
  },
  {
    path: '/event-detail-:id',
    name: 'EventDetail',
    component: EventDetail
  },
  { path: '/', 
    name: 'Home', 
    component: Home 
  },
  { path: '/make-appointment', 
    name: 'MakeAppointment', 
    component: MakeAppointment
  },
  { path: '/edit-category-:id', 
  name: 'EditCategory', 
  component: EditCategory
  },
  { path: '/users-schedule', 
  name: 'UsersList', 
  component: UsersList
  },
  { path: '/users-detail-:id', 
  name: 'UserDetail', 
  component: UserDetail
  },
  { path: '/create-users', 
  name: 'CreateUser', 
  component: CreateUser
  },
  { path: '/sign-in', 
  name: 'SignIn', 
  component: SignIn
  },
  {
    path: '/:catchNotMatchPath(.*)',
    name: 'NotFound',
    component: NotFound
  }
]
const router = createRouter({ history, routes })
export default router
