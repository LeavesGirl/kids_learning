<template>
  <div class="poetry-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="诗词详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="poetry" shadow="hover" class="detail-card">
      <div class="poetry-header">
        <h2 class="poetry-title">{{ poetry.title }}</h2>
        <div class="poetry-meta">
          <el-tag size="large" effect="dark" class="dynasty-tag">{{ poetry.dynasty }}</el-tag>
          <span class="author">{{ poetry.author }}</span>
        </div>
      </div>

      <el-divider />

      <div class="poetry-content">
        <div class="content-wrapper">
          <p v-for="(line, index) in poetry.content.split('。').filter(Boolean)" :key="index" class="poetry-line">
            {{ line }}。
          </p>
        </div>
      </div>

      <el-divider v-if="poetry.tags" />

      <div class="poetry-tags" v-if="poetry.tags">
        <h4 class="tags-title">
          <el-icon><Collection /></el-icon>
          标签
        </h4>
        <div class="tags-list">
          <el-tag
            v-for="tag in poetry.tags.split(',')"
            :key="tag"
            size="large"
            effect="plain"
            class="tag-item"
          >
            {{ tag }}
          </el-tag>
        </div>
      </div>

      <div class="card-actions">
        <el-button size="large" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>
    </el-card>

    <el-empty v-else description="诗词不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { poetryApi } from '@/api/poetry'
import { Collection, ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const poetry = ref(null)

const goBack = () => {
  router.push('/poetry')
}

const loadPoetryDetail = async () => {
  try {
    const id = route.params.id
    const response = await poetryApi.detail(id)
    poetry.value = response
  } catch (error) {
    console.error('Failed to load poetry detail:', error)
  }
}

onMounted(() => {
  loadPoetryDetail()
})
</script>

<style scoped>
.poetry-detail {
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
  border: 2px solid #FFB6C1;
}

.poetry-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #FFF0F5 0%, #FFE4E1 100%);
  margin: -20px -20px 0 -20px;
  position: relative;
}

.poetry-title {
  font-size: 2.2rem;
  font-weight: bold;
  margin: 0 0 20px 0;
  color: #DB7093;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.poetry-meta {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.dynasty-tag {
  background-color: #FF69B4 !important;
  border-color: #FF69B4 !important;
}

.author {
  font-size: 1.2rem;
  font-weight: 500;
  color: #DB7093;
}

.poetry-content {
  padding: 40px 20px;
}

.content-wrapper {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
}

.poetry-line {
  font-size: 1.3rem;
  line-height: 2.2;
  color: #303133;
  margin: 0;
  padding: 8px 0;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
  letter-spacing: 2px;
}

.poetry-tags {
  padding: 20px;
  background: linear-gradient(135deg, #FFF0F5 0%, #FFE4E1 100%);
  border-radius: 12px;
  margin: 0 20px 20px 20px;
  border: 1px solid #FFB6C1;
}

.tags-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 1rem;
  font-weight: bold;
  color: #DB7093;
  margin: 0 0 16px 0;
}

.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag-item {
  border-radius: 20px;
  color: #FF69B4;
  border-color: #FFB6C1;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #FFB6C1;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
  background-color: #FF69B4;
  border-color: #FF69B4;
  color: #fff;
}

.back-btn:hover {
  background-color: #DB7093;
  border-color: #DB7093;
}

.empty-state {
  padding: 60px 0;
}
</style>