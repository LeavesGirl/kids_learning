<template>
  <div class="math-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="题目详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="math" shadow="hover" class="detail-card">
      <div class="math-header">
        <h2 class="math-title">{{ math.title }}</h2>
      </div>

      <el-divider />

      <div class="math-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="题目">
            <span class="content-text">{{ math.content }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="解答">
            <span class="solution-text">{{ math.solution }}</span>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="card-actions">
        <el-button type="primary" size="large" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>
    </el-card>

    <el-empty v-else description="题目不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { mathApi } from '@/api/math'
import { ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const math = ref(null)

const goBack = () => {
  router.push('/math')
}

const loadMathDetail = async () => {
  try {
    const id = route.params.id
    const response = await mathApi.detail(id)
    math.value = response
  } catch (error) {
    console.error('Failed to load math detail:', error)
  }
}

onMounted(() => {
  loadMathDetail()
})
</script>

<style scoped>
.math-detail {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px 0;
}

.page-header {
  margin-bottom: 24px;
}

.page-header-content {
  padding: 0;
}

.detail-card {
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.math-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #06B6D4 0%, #0891B2 100%);
  color: white;
  margin: -20px -20px 0 -20px;
}

.math-title {
  font-size: 2rem;
  font-weight: bold;
  margin: 0;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
}

.math-content {
  padding: 30px 20px;
}

:deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
  background-color: #f5f7fa;
}

.content-text {
  font-size: 1.1rem;
  line-height: 1.6;
}

.solution-text {
  font-size: 1.1rem;
  font-weight: 500;
  color: #06B6D4;
  line-height: 1.6;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #e4e7ed;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
}

.empty-state {
  padding: 60px 0;
}
</style>