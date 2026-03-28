<template>
  <div class="idiom-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#FFA07A' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="成语名称">
              <el-input
                v-model="searchForm.name"
                placeholder="请输入成语名称"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="释义">
              <el-input
                v-model="searchForm.meaning"
                placeholder="请输入释义"
                clearable
                :prefix-icon="Document"
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
                  :style="{ backgroundColor: '#FF6347', borderColor: '#FF6347', color: '#fff' }" 
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

    <!-- 成语列表 -->
    <el-empty v-if="idiomList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="idiom-grid">
      <div
        v-for="idiom in idiomList"
        :key="idiom.id"
        class="idiom-item"
      >
        <el-card
          class="idiom-card"
          @click="goToDetail(idiom.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#FFA07A' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #FFF5EE 0%, #FFE4E1 100%)' }">
              <h3 class="idiom-name" :style="{ color: '#DC143C' }">{{ idiom.idiom }}</h3>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#FFFAFA' }">
              <div class="idiom-meaning">
                <p class="meaning-text">{{ idiom.meaning }}</p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="idiom.learned ? { backgroundColor: '#52c41a', borderColor: '#52c41a', color: '#fff' } : { backgroundColor: '#ff4d4f', borderColor: '#ff4d4f', color: '#fff' }"
                size="small"
                @click.stop="toggleLearned(idiom)"
              >
                <el-icon><Check v-if="idiom.learned" /><CircleClose v-else /></el-icon>
                {{ idiom.learned ? '已学' : '未学' }}
              </el-button>
              <el-button
                :style="{ color: '#FF6347' }"
                size="small"
                text
                @click.stop="goToDetail(idiom.id)"
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
    <el-card v-if="idiomList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#FFA07A' }">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
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
import { idiomApi } from '@/api/idiom'
import { Search, RefreshRight, Document, Collection, ArrowRight, Check, CircleClose } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  name: '',
  meaning: '',
  learned: null
})

const idiomList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/idiom/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadIdiomList()
}

const reset = () => {
  searchForm.value = {
    name: '',
    meaning: '',
    learned: null
  }
  pageNum.value = 1
  loadIdiomList()
}

const toggleLearned = async (idiom) => {
  try {
    const newLearned = !idiom.learned
    await idiomApi.update(idiom.id, { id: idiom.id, learned: newLearned })
    idiom.learned = newLearned
    ElMessage.success(newLearned ? '已标记为已学' : '已标记为未学')
  } catch (error) {
    console.error('Failed to toggle learned status:', error)
    ElMessage.error('操作失败')
  }
}

const loadIdiomList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await idiomApi.list(params)
    idiomList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load idiom list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadIdiomList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadIdiomList()
}

onMounted(() => {
  loadIdiomList()
})
</script>

<style scoped>
.idiom-view {
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
  color: #F59E0B;
}

.idiom-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .idiom-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .idiom-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .idiom-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .idiom-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.idiom-item {
  display: flex;
  width: 100%;
}

.idiom-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
  min-height: 280px;
  width: 100%;
  flex: 1;
}

.idiom-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.card-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header {
  padding: 25px 20px;
  color: white;
  text-align: center;
  flex-shrink: 0;
  min-height: 90px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.idiom-name {
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0;
  color: white;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
  word-break: keep-all;
}

.card-body {
  padding: 20px;
  background-color: #fafafa;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 120px;
  overflow: hidden;
}

.idiom-meaning {
  text-align: center;
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.meaning-text {
  margin: 0;
  color: #606266;
  font-size: 0.95rem;
  line-height: 1.5;
  word-break: break-word;
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