<template>
  <div class="idiom-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="成语详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="idiom" shadow="hover" class="detail-card">
      <div class="idiom-header">
        <h2 class="idiom-name">{{ idiom.idiom }}</h2>
      </div>

      <el-divider />

      <div class="idiom-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="成语">
            <span class="name-text">{{ idiom.idiom }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="读音">
            <span class="pinyin-text">{{ idiom.pinyin || '-' }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="释义">
            {{ idiom.meaning || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="出处">
            {{ idiom.origin || '-' }}
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="card-actions">
        <el-button size="large" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>
    </el-card>

    <el-empty v-else description="成语不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { idiomApi } from '@/api/idiom'
import { ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const idiom = ref(null)

const goBack = () => {
  router.push('/idiom')
}

const loadIdiomDetail = async () => {
  try {
    const id = route.params.id
    const response = await idiomApi.detail(id)
    idiom.value = response
  } catch (error) {
    console.error('Failed to load idiom detail:', error)
  }
}

onMounted(() => {
  loadIdiomDetail()
})
</script>

<style scoped>
.idiom-detail {
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
  border: 2px solid #FFA07A;
}

.idiom-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #FFF5EE 0%, #FFE4E1 100%);
  margin: -20px -20px 0 -20px;
}

.idiom-name {
  font-size: 3.5rem;
  font-weight: bold;
  margin: 0;
  color: #DC143C;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.idiom-content {
  padding: 30px 20px;
}

:deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
  background: linear-gradient(135deg, #FFF5EE 0%, #FFE4E1 100%);
  color: #DC143C;
}

:deep(.el-descriptions__content) {
  background-color: #fff;
}

.name-text {
  font-size: 1.5rem;
  font-weight: bold;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
  color: #DC143C;
}

.pinyin-text {
  font-size: 1.2rem;
  color: #FF6347;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #FFA07A;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
  background-color: #FF6347;
  border-color: #FF6347;
  color: #fff;
}

.back-btn:hover {
  background-color: #DC143C;
  border-color: #DC143C;
}

.empty-state {
  padding: 60px 0;
}
</style>