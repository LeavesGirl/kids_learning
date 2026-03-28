<template>
  <div class="drawing-detail">
    <el-page-header
      @back="goBack"
      content="画画详情"
    />
    <div class="drawing-content">
      <el-empty description="画画详情功能开发中" />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/drawing')
}
</script>

<style scoped>
.drawing-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px 0;
}
</style>