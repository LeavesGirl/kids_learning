<template>
  <div class="english-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="单词详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="english" shadow="hover" class="detail-card">
      <div class="english-header">
        <h2 class="english-word">{{ english.word }}</h2>
        <p class="english-phonetic">{{ english.pronunciation }}</p>
      </div>

      <el-divider />

      <div class="english-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="单词">
            <span class="word-text">{{ english.word }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="音标">
            <span class="phonetic-text">{{ english.pronunciation }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="类型">
            <el-tag :type="getTypeColor(english.type)" effect="plain">
              {{ getTypeName(english.type) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="释义">
            {{ english.meaning || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="例句">
            {{ english.example || '-' }}
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

    <el-empty v-else description="单词不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { englishApi } from '@/api/english'
import { ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const english = ref(null)

const goBack = () => {
  router.push('/english')
}

const getTypeName = (type) => {
  const typeMap = {
    'animal': '动物',
    'fruit': '水果',
    'color': '颜色',
    'number': '数字',
    'body': '身体',
    'food': '食物',
    'nature': '自然',
    'family': '家庭',
    'school': '学校',
    'action': '动作',
    'adjective': '形容词'
  }
  return typeMap[type] || type
}

const getTypeColor = (type) => {
  const colorMap = {
    'animal': 'success',
    'fruit': 'warning',
    'color': 'danger',
    'number': 'primary',
    'body': 'info',
    'food': 'warning',
    'nature': 'success',
    'family': 'primary',
    'school': 'info',
    'action': 'warning',
    'adjective': 'danger'
  }
  return colorMap[type] || 'info'
}

const loadEnglishDetail = async () => {
  try {
    const id = route.params.id
    const response = await englishApi.detail(id)
    english.value = response
  } catch (error) {
    console.error('Failed to load english detail:', error)
  }
}

onMounted(() => {
  loadEnglishDetail()
})
</script>

<style scoped>
.english-detail {
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
  border: 2px solid #87CEEB;
}

.english-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #F0F8FF 0%, #E6F3FF 100%);
  margin: -20px -20px 0 -20px;
}

.english-word {
  font-size: 4rem;
  font-weight: bold;
  margin: 0 0 10px 0;
  color: #1E90FF;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.english-phonetic {
  font-size: 1.5rem;
  font-weight: 500;
  margin: 0;
  color: #1E90FF;
}

.english-content {
  padding: 30px 20px;
}

:deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
  background: linear-gradient(135deg, #F0F8FF 0%, #E6F3FF 100%);
  color: #1E90FF;
}

:deep(.el-descriptions__content) {
  background-color: #fff;
}

.word-text {
  font-size: 1.5rem;
  font-weight: bold;
  color: #4169E1;
}

.phonetic-text {
  font-size: 1.2rem;
  color: #1E90FF;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #87CEEB;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
  background-color: #4169E1;
  border-color: #4169E1;
  color: #fff;
}

.back-btn:hover {
  background-color: #1E90FF;
  border-color: #1E90FF;
}

.empty-state {
  padding: 60px 0;
}
</style>