<template>
  <div class="drawing-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#DDA0DD' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="主题">
              <el-input
                v-model="searchForm.title"
                placeholder="请输入主题"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="开始日期">
              <el-date-picker
                v-model="searchForm.startDate"
                type="date"
                placeholder="选择开始日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="结束日期">
              <el-date-picker
                v-model="searchForm.endDate"
                type="date"
                placeholder="选择结束日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="操作">
              <el-button-group style="width: 100%">
                <el-button 
                  :style="{ backgroundColor: '#9370DB', borderColor: '#9370DB', color: '#fff' }" 
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

    <!-- 画画列表 -->
    <el-empty v-if="drawingList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="drawing-grid">
      <div
        v-for="drawing in drawingList"
        :key="drawing.id"
        class="drawing-item"
      >
        <el-card
          class="drawing-card"
          @click="goToDetail(drawing.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#DDA0DD' }"
        >
          <div class="card-wrapper">
            <div class="card-image">
              <img :src="drawing.imagePath" :alt="drawing.title" class="drawing-img" />
            </div>
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #F8F0FF 0%, #F0E6FF 100%)' }">
              <h3 class="drawing-title" :style="{ color: '#8A2BE2' }">{{ drawing.title }}</h3>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#FFFAFF' }">
              <div class="drawing-description">
                <p class="description-text">{{ drawing.description }}</p>
              </div>
            </div>
            <div class="card-footer">
              <p class="date-text" v-if="drawing.createdAt" :style="{ color: '#9370DB' }">
                创建时间：{{ formatDate(drawing.createdAt) }}
              </p>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 分页 -->
    <el-card v-if="drawingList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#DDA0DD' }">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 15, 20, 30]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        background
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { drawingApi } from '@/api/drawing'
import { Search, RefreshRight } from '@element-plus/icons-vue'

const router = useRouter()

const searchForm = ref({
  title: '',
  startDate: '',
  endDate: ''
})

const drawingList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const goToDetail = (id) => {
  router.push(`/drawing/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadDrawingList()
}

const reset = () => {
  searchForm.value = {
    title: '',
    startDate: '',
    endDate: ''
  }
  pageNum.value = 1
  loadDrawingList()
}

const loadDrawingList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await drawingApi.list(params)
    drawingList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load drawing list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadDrawingList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadDrawingList()
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

onMounted(() => {
  loadDrawingList()
})
</script>

<style scoped>
.drawing-view {
  max-width: 1400px;
  margin: 0 auto;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.search-form {
  padding: 10px 0;
}

.drawing-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .drawing-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .drawing-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .drawing-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .drawing-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.drawing-item {
  display: flex;
  width: 100%;
}

.drawing-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
  min-height: 320px;
  width: 100%;
  flex: 1;
}

.drawing-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.card-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-image {
  width: 100%;
  height: 180px;
  overflow: hidden;
  flex-shrink: 0;
}

.drawing-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-header {
  padding: 15px 20px;
  text-align: center;
  flex-shrink: 0;
}

.drawing-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin: 0;
  color: #303133;
}

.card-body {
  padding: 0 20px 15px;
  background-color: #fafafa;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 60px;
}

.drawing-description {
  text-align: center;
  width: 100%;
}

.description-text {
  margin: 0;
  color: #606266;
  font-size: 0.9rem;
  line-height: 1.4;
}

.card-footer {
  padding: 10px 20px;
  border-top: 1px solid #ebeef5;
  text-align: center;
  background-color: white;
  flex-shrink: 0;
}

.date-text {
  margin: 0;
  color: #909399;
  font-size: 0.85rem;
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
