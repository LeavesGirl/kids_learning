<template>
  <div class="character-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="汉字详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="character" shadow="hover" class="detail-card">
      <div class="character-header">
        <h2 class="character-text">{{ character.character }}</h2>
        <p class="character-pinyin">{{ character.pinyin }}</p>
      </div>

      <el-divider />

      <div class="character-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="部首" class="label-cell">{{ character.radical || '-' }}</el-descriptions-item>
          <el-descriptions-item label="笔画" class="label-cell">{{ character.strokes || '-' }} 画</el-descriptions-item>
          <el-descriptions-item label="释义" class="label-cell">{{ character.meaning || '-' }}</el-descriptions-item>
          <el-descriptions-item label="组词" class="label-cell">{{ character.words || '-' }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="card-actions">
        <el-button size="large" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>
    </el-card>

    <el-empty v-else description="汉字不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { characterApi } from '@/api/character'
import { ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const character = ref(null)

const goBack = () => {
  router.push('/character')
}

const loadCharacterDetail = async () => {
  try {
    const id = route.params.id
    const response = await characterApi.detail(id)
    character.value = response
  } catch (error) {
    console.error('Failed to load character detail:', error)
  }
}

onMounted(() => {
  loadCharacterDetail()
})
</script>

<style scoped>
.character-detail {
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
  border: 2px solid #90EE90;
}

.character-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #F0FFF0 0%, #E0FFE0 100%);
  margin: -20px -20px 0 -20px;
}

.character-text {
  font-size: 5rem;
  font-weight: bold;
  margin: 0 0 10px 0;
  color: #228B22;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.character-pinyin {
  font-size: 1.5rem;
  font-weight: 500;
  margin: 0;
  color: #228B22;
}

.character-content {
  padding: 30px 20px;
}

:deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
  background: linear-gradient(135deg, #F0FFF0 0%, #E0FFE0 100%);
  color: #228B22;
}

:deep(.el-descriptions__content) {
  background-color: #fff;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #90EE90;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
  background-color: #32CD32;
  border-color: #32CD32;
  color: #fff;
}

.back-btn:hover {
  background-color: #228B22;
  border-color: #228B22;
}

.empty-state {
  padding: 60px 0;
}
</style>