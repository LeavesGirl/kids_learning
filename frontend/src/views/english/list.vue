<template>
  <div class="english-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#87CEEB' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="英文单词">
              <el-input
                v-model="searchForm.word"
                placeholder="请输入英文单词"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="中文释义">
              <el-input
                v-model="searchForm.meaning"
                placeholder="请输入中文释义"
                clearable
                :prefix-icon="Document"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="4" :lg="3">
            <el-form-item label="单词类别">
              <el-select
                v-model="searchForm.type"
                placeholder="请选择类别"
                clearable
                style="width: 100%"
              >
                <el-option label="动物" value="animal" />
                <el-option label="水果" value="fruit" />
                <el-option label="颜色" value="color" />
                <el-option label="数字" value="number" />
                <el-option label="身体" value="body" />
                <el-option label="食物" value="food" />
                <el-option label="自然" value="nature" />
                <el-option label="家庭" value="family" />
                <el-option label="学校" value="school" />
                <el-option label="动作" value="action" />
                <el-option label="形容词" value="adjective" />
              </el-select>
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
                  :style="{ backgroundColor: '#4169E1', borderColor: '#4169E1', color: '#fff' }" 
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

    <!-- 英文单词列表 -->
    <el-empty v-if="englishList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="english-grid">
      <div
        v-for="english in englishList"
        :key="english.id"
        class="english-item"
      >
        <el-card
          class="english-card"
          @click="goToDetail(english.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#87CEEB' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #F0F8FF 0%, #E6F3FF 100%)' }">
              <h3 class="english-word" :style="{ color: '#1E90FF' }">{{ english.word }}</h3>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#F0FFFF' }">
              <div class="english-phonetic-container">
                <p class="english-phonetic" :style="{ color: '#1E90FF' }">{{ english.pronunciation }}</p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="english.learned ? { backgroundColor: '#52c41a', borderColor: '#52c41a', color: '#fff' } : { backgroundColor: '#ff4d4f', borderColor: '#ff4d4f', color: '#fff' }"
                size="small"
                @click.stop="toggleLearned(english)"
              >
                <el-icon><Check v-if="english.learned" /><CircleClose v-else /></el-icon>
                {{ english.learned ? '已学' : '未学' }}
              </el-button>
              <el-button
                :style="{ color: '#4169E1' }"
                size="small"
                text
                @click.stop="goToDetail(english.id)"
              >
                查看详情
                <el-icon class="el-icon--right"><ArrowRight /></el-icon>
              </el-button>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 分页 -->
    <el-card v-if="englishList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#87CEEB' }">
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
import { englishApi } from '@/api/english'
import { Search, RefreshRight, Document, Collection, ArrowRight, Check, CircleClose } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  word: '',
  meaning: '',
  type: '',
  learned: null
})

const englishList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/english/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadEnglishList()
}

const reset = () => {
  searchForm.value = {
    word: '',
    meaning: '',
    type: '',
    learned: null
  }
  pageNum.value = 1
  loadEnglishList()
}

const toggleLearned = async (english) => {
  try {
    const newLearned = !english.learned
    await englishApi.update(english.id, { id: english.id, learned: newLearned })
    english.learned = newLearned
    ElMessage.success(newLearned ? '已标记为已学' : '已标记为未学')
  } catch (error) {
    console.error('Failed to toggle learned status:', error)
    ElMessage.error('操作失败')
  }
}

const loadEnglishList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await englishApi.list(params)
    englishList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load english list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadEnglishList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadEnglishList()
}

onMounted(() => {
  loadEnglishList()
})
</script>

<style scoped>
.english-view {
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
  color: #A78BFA;
}

.english-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .english-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .english-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .english-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .english-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.english-item {
  display: flex;
}

.english-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
  width: 100%;
}

.english-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.card-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header {
  padding: 30px 20px;
  color: white;
  text-align: center;
  flex-shrink: 0;
}

.english-word {
  font-size: 2rem;
  font-weight: bold;
  margin: 0 0 8px 0;
  color: white;
}

.card-body {
  padding: 20px;
  background-color: #fafafa;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 80px;
}

.english-phonetic-container {
  text-align: center;
  width: 100%;
}

.english-phonetic {
  margin: 0;
  font-size: 1.2rem;
  color: #606266;
  font-style: italic;
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