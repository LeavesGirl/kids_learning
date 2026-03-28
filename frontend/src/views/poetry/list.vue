<template>
  <div class="poetry-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#FFB6C1' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="诗词标题">
              <el-input
                v-model="searchForm.title"
                placeholder="请输入诗词标题"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="诗词内容">
              <el-input
                v-model="searchForm.content"
                placeholder="请输入诗词内容"
                clearable
                :prefix-icon="Document"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="作者">
              <el-input
                v-model="searchForm.author"
                placeholder="请输入作者"
                clearable
                :prefix-icon="User"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="4" :lg="3">
            <el-form-item label="学习状态">
              <el-select
                v-model="searchForm.learned"
                placeholder="请选择学习状态"
                clearable
                style="width: 100%"
              >
                <el-option label="已学" :value="true" />
                <el-option label="未学" :value="false" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="操作">
              <el-button-group style="width: 100%">
                <el-button 
                  :style="{ backgroundColor: '#FF69B4', borderColor: '#FF69B4', color: '#fff' }" 
                  @click="search" 
                  :icon="Search" 
                  style="flex: 1"
                >
                  搜索
                </el-button>
                <el-button @click="reset" :icon="RefreshRight" style="flex: 1">
                  重置
                </el-button>
              </el-button-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 诗词列表 -->
    <el-empty v-if="poetryList.length === 0" description="暂无数据" :image-size="200" />
    
    <el-row v-else :gutter="24" class="poetry-list">
      <el-col
        v-for="poetry in poetryList"
        :key="poetry.id"
        :xs="24"
        :sm="12"
        :md="8"
        class="poetry-col"
      >
        <el-card
          class="poetry-card"
          @click="goToDetail(poetry.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#FFB6C1' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #FFF0F5 0%, #FFE4E1 100%)' }">
              <el-tag size="small" effect="dark" class="dynasty-tag" :style="{ backgroundColor: '#DB7093', borderColor: '#DB7093' }">
                {{ poetry.dynasty }}
              </el-tag>
              <h3 class="poetry-title" :style="{ color: '#DB7093' }">{{ poetry.title }}</h3>
              <p class="poetry-author" :style="{ color: '#666' }">
                <el-icon :style="{ color: '#DB7093' }"><User /></el-icon>
                {{ poetry.author }}
              </p>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#FFFAFA' }">
              <div class="poetry-content">
                <p
                  v-for="(line, index) in poetry.content.split('。').filter(Boolean).slice(0, 4)"
                  :key="index"
                  class="poetry-line"
                >
                  {{ line }}。
                </p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="poetry.learned ? { backgroundColor: '#52c41a', borderColor: '#52c41a', color: '#fff' } : { backgroundColor: '#ff4d4f', borderColor: '#ff4d4f', color: '#fff' }"
                size="small"
                @click.stop="toggleLearned(poetry)"
              >
                <el-icon><Check v-if="poetry.learned" /><CircleClose v-else /></el-icon>
                {{ poetry.learned ? '已学' : '未学' }}
              </el-button>
              <el-button
                :style="{ color: '#FF69B4' }"
                size="small"
                text
                @click.stop="goToDetail(poetry.id)"
              >
                查看详情
                <el-icon class="el-icon--right"><ArrowRight /></el-icon>
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 分页 -->
    <el-card v-if="poetryList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#FFB6C1' }">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[6, 12, 18, 24]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        background
        popper-class="pagination-popper"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { poetryApi } from '@/api/poetry'
import { Search, RefreshRight, Document, Collection, User, ArrowRight, Check, CircleClose } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  title: '',
  content: '',
  author: '',
  learned: null
})

const poetryList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(6)

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/poetry/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadPoetryList()
}

const reset = () => {
  searchForm.value = {
    title: '',
    content: '',
    author: '',
    learned: null
  }
  pageNum.value = 1
  loadPoetryList()
}

const toggleLearned = async (poetry) => {
  try {
    const newLearned = !poetry.learned
    await poetryApi.update(poetry.id, { id: poetry.id, learned: newLearned })
    poetry.learned = newLearned
    ElMessage.success(newLearned ? '已标记为已学' : '已标记为未学')
  } catch (error) {
    console.error('Failed to toggle learned:', error)
    ElMessage.error('操作失败')
  }
}

const loadPoetryList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await poetryApi.list(params)
    poetryList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load poetry list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadPoetryList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadPoetryList()
}

onMounted(() => {
  loadPoetryList()
})
</script>

<style scoped>
.poetry-view {
  max-width: 1400px;
  margin: 0 auto;
}

.page-title-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: #303133;
}

.search-form {
  padding: 10px 0;
}

.stats-row {
  margin-bottom: 20px;
}

:deep(.el-statistic__content) {
  font-size: 24px;
  font-weight: bold;
  color: #667eea;
}

.poetry-list {
  margin-bottom: 20px;
}

.poetry-col {
  margin-bottom: 24px;
}

.poetry-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
}

.poetry-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.card-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header {
  padding: 24px;
  color: white;
  text-align: center;
  position: relative;
  flex-shrink: 0;
}

.dynasty-tag {
  position: absolute;
  top: 12px;
  right: 12px;
}

.poetry-title {
  font-size: 1.3rem;
  font-weight: bold;
  margin: 0 0 8px 0;
  color: white;
}

.poetry-author {
  margin: 0;
  font-size: 0.95rem;
  opacity: 0.9;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}

.card-body {
  padding: 20px;
  background-color: #fafafa;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 140px;
}

.poetry-content {
  text-align: center;
  width: 100%;
}

.poetry-line {
  margin: 0;
  padding: 4px 0;
  color: #606266;
  font-size: 0.95rem;
  line-height: 1.6;
}

.card-footer {
  padding: 12px 20px;
  border-top: 1px solid #ebeef5;
  display: flex;
  justify-content: flex-end;
  background-color: white;
  flex-shrink: 0;
}

.pagination-card {
  border-radius: 12px;
  display: flex;
  justify-content: center;
}

:deep(.el-pagination) {
  justify-content: center;
}
</style>