<template>
  <div>123</div>
</template>
<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import axios from '@/axios/index';
import { formContextKey, type FormInstance, type FormRules } from 'element-plus'
export default defineComponent({
  setup() {
    const store = useStore();
    const router = useRouter();
    const route = useRoute();

    return {

    }
  }
});
</script>