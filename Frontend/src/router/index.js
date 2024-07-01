import { createRouter, createWebHistory } from 'vue-router'
import HomeView  from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import AddChocholate from '../components/AddChocholate.vue'
import FactoryView from '../views/FactoryView.vue'
import AddFactory from '@/components/AddFactory.vue'
import ShoppingCart from '../components/ShoppingCart.vue'
import Profile from '../components/Profile.vue'
import Orders from '../components/Orders.vue'
import FactorySearchView from '../views/FactorySearchView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView
    },
    {
      path: "/login",
      name: "login",
      component: LoginView
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView
    },
    {
      path: "/add-chocholate",
      name: "addChocholate",
      component: AddChocholate
    },
    {
      path: '/factory/:id',
      name: 'factory',
      component: FactoryView
    },
    {
      path: '/add-factory',
      name: 'addFactory',
      component: AddFactory
    },
    {
      path: '/shopping-cart',
      name: 'shopping-cart',
      component: ShoppingCart
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile
    },
    {
      path: '/orders',
      name: 'orders',
      component: Orders
    },
    {
      path: '/factory-search',
      name: 'factory-search',
      component: FactorySearchView,
      props: route => ({ search: route.query.search })
    }
  ]
})

export default router
