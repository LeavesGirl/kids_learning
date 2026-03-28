<template>
  <div class="math-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#00CED1' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="关键词">
              <el-input
                v-model="searchForm.keyword"
                placeholder="请输入关键词"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="操作">
              <el-button-group style="width: 100%">
                <el-button 
                  :style="{ backgroundColor: '#20B2AA', borderColor: '#20B2AA', color: '#fff' }" 
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
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="操作">
              <el-button-group style="width: 100%">
                <el-button 
                  :style="{ backgroundColor: '#008B8B', borderColor: '#008B8B', color: '#fff' }" 
                  @click="openGenerateDialog" 
                  :icon="DocumentAdd" 
                  style="flex: 1"
                >
                  生成题目
                </el-button>
              </el-button-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    
    <!-- 生成题目弹框 -->
    <el-dialog
      v-model="generateDialogVisible"
      title="生成算术题目"
      width="500px"
    >
      <el-form :model="generateForm" label-width="100px">
        <el-form-item label="最大数值(n)">
          <el-input-number
            v-model="generateForm.maxNumber"
            :min="1"
            :max="1000"
            :step="1"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="题目数量(m)">
          <el-input-number
            v-model="generateForm.count"
            :min="1"
            :max="50"
            :step="1"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="运算符">
          <el-checkbox-group v-model="generateForm.operators">
            <el-checkbox label="add">加法</el-checkbox>
            <el-checkbox label="subtract">减法</el-checkbox>
            <el-checkbox label="multiply">乘法</el-checkbox>
            <el-checkbox label="divide">除法</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="generateDialogVisible = false">取消</el-button>
        <el-button :style="{ backgroundColor: '#20B2AA', borderColor: '#20B2AA', color: '#fff' }" @click="generateQuestions" :loading="generating">
          生成
        </el-button>
      </template>
    </el-dialog>

    <!-- 数学题目列表 -->
    <el-empty v-if="mathList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="math-grid">
      <div
        v-for="math in mathList"
        :key="math.id"
        class="math-item"
      >
        <el-card
          class="math-card"
          @click="goToDetail(math.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#00CED1' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #F0FFFF 0%, #E0FFFF 100%)' }">
              <h3 class="math-title" :style="{ color: '#008B8B' }">{{ math.title }}</h3>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#F5FFFA' }">
              <div class="math-content">
                <p class="content-label" :style="{ color: '#20B2AA' }">题目：</p>
                <p class="content-text">{{ math.content }}</p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="{ color: '#20B2AA' }"
                size="small"
                text
                @click.stop="goToDetail(math.id)"
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
    <el-card v-if="mathList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#00CED1' }">
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
import { mathApi } from '@/api/math'
import { Search, RefreshRight, Collection, ArrowRight, DocumentAdd } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  keyword: ''
})

const mathList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const generateDialogVisible = ref(false)
const generating = ref(false)
const generateForm = ref({
  maxNumber: 10,
  count: 5,
  operators: ['add']
})

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/math/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadMathList()
}

const reset = () => {
  searchForm.value = {
    keyword: ''
  }
  pageNum.value = 1
  loadMathList()
}

const loadMathList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await mathApi.list(params)
    mathList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load math list:', error)
  }
}

const openGenerateDialog = () => {
  generateForm.value = {
    maxNumber: 10,
    count: 5,
    operators: ['add']
  }
  generateDialogVisible.value = true
}

const generateQuestions = async () => {
  if (generateForm.value.operators.length === 0) {
    ElMessage.warning('请至少选择一种运算符')
    return
  }
  
  generating.value = true
  try {
    await mathApi.generate(generateForm.value)
    ElMessage.success('题目生成成功！')
    generateDialogVisible.value = false
    pageNum.value = 1
    loadMathList()
  } catch (error) {
    console.error('Failed to generate questions:', error)
    ElMessage.error('题目生成失败，请重试')
  } finally {
    generating.value = false
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadMathList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadMathList()
}

onMounted(() => {
  loadMathList()
})
</script>

<style scoped>
.math-view {
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
  color: #06B6D4;
}

.math-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .math-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .math-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .math-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .math-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.math-item {
  display: flex;
  width: 100%;
}

.math-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
  min-height: 280px;
  width: 100%;
  flex: 1;
}

.math-card:hover {
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

.math-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0;
  color: white;
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

.math-content {
  text-align: center;
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.content-label {
  margin: 0 0 8px 0;
  color: #909399;
  font-size: 0.9rem;
}

.content-text {
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