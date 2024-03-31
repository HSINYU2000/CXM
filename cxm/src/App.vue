<template>
  <!-- <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav> -->
  <!-- <router-view/> -->
  <loginView v-if="!loginStatus" />

  <indexView v-else />
</template>
<script lang="ts">
import { computed, defineComponent, ref, watchEffect } from 'vue';
import loginView from '@/views/Login/loginView.vue'
import indexView from '@/views/Index/indexView.vue'
import { useStore } from 'vuex';
export default defineComponent({

  components: {
    loginView,
    indexView
  },
  setup() {
    const store = useStore();
    const loginStatus = computed(() => {
      return store.getters['login'];
    }

    )

    return {
      loginStatus
    }
  }

});
</script>
<style lang="scss">
#app {
  font-family: Microsoft JhengHei, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  width: 100%;
}

html,
body {
  padding: 0;
  margin: 0;
  height: 100%;
  overflow: none;
}

// nav {
//   padding: 30px;

//   a {
//     font-weight: bold;
//     color: #2c3e50;

//     &.router-link-exact-active {
//       color: #42b983;
//     }
//   }
// }
</style>
