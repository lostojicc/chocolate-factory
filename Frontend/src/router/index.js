import { createRouter, createWebHistory } from 'vue-router'
import HomeView  from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import AddChocholate from '../components/AddChocholate.vue'
import FactoryView from '../views/FactoryView.vue'
import AddFactory from '@/components/AddFactory.vue'
import ShoppingCart from '../components/ShoppingCart.vue'

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
    }
  ]
})

export default router
