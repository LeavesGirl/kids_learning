<template>
  <div class="pinyin-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#FFD700' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="拼音">
              <el-input
                v-model="searchForm.pinyin"
                placeholder="请输入拼音"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="4" :lg="3">
            <el-form-item label="类型">
              <el-select
                v-model="searchForm.type"
                placeholder="请选择类型"
                clearable
                style="width: 100%"
              >
                <el-option label="元音" value="vowel" />
                <el-option label="辅音" value="consonant" />
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
                  :style="{ backgroundColor: '#FFA500', borderColor: '#FFA500', color: '#fff' }" 
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

    <!-- 拼音列表 -->
    <el-empty v-if="pinyinList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="pinyin-grid">
      <div
        v-for="pinyin in pinyinList"
        :key="pinyin.id"
        class="pinyin-item"
      >
        <el-card
          class="pinyin-card"
          @click="goToDetail(pinyin.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#FFD700' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #FFFAF0 0%, #FFF8DC 100%)' }">
              <h3 class="pinyin-text" :style="{ color: '#FF8C00' }">{{ pinyin.pinyin }}</h3>
              <div class="tag-container">
                <el-tag size="small" effect="dark" class="type-tag" :style="{ backgroundColor: pinyin.type === 'vowel' ? '#32CD32' : '#87CEEB', borderColor: pinyin.type === 'vowel' ? '#32CD32' : '#87CEEB' }">
                  {{ pinyin.type === 'vowel' ? '元音' : '辅音' }}
                </el-tag>
              </div>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#FFFAF0' }">
              <div class="pinyin-example">
                <p class="example-label">示例汉字：</p>
                <p class="example-text" :style="{ color: '#FF8C00' }">{{ pinyin.character }}</p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="pinyin.learned ? { backgroundColor: '#52c41a', borderColor: '#52c41a', color: '#fff' } : { backgroundColor: '#ff4d4f', borderColor: '#ff4d4f', color: '#fff' }"
                size="small"
                @click.stop="toggleLearned(pinyin)"
              >
                <el-icon><Check v-if="pinyin.learned" /><CircleClose v-else /></el-icon>
                {{ pinyin.learned ? '已学' : '未学' }}
              </el-button>
              <el-button
                :style="{ color: '#FFA500' }"
                size="small"
                text
                @click.stop="goToDetail(pinyin.id)"
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
    <el-card v-if="pinyinList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#FFD700' }">
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
import { pinyinApi } from '@/api/pinyin'
import { Search, RefreshRight, Collection, ArrowRight, Check, CircleClose } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  pinyin: '',
  type: '',
  learned: null
})

const pinyinList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/pinyin/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadPinyinList()
}

const reset = () => {
  searchForm.value = {
    pinyin: '',
    type: '',
    learned: null
  }
  pageNum.value = 1
  loadPinyinList()
}

const toggleLearned = async (pinyin) => {
  try {
    const newLearned = !pinyin.learned
    await pinyinApi.update(pinyin.id, { id: pinyin.id, learned: newLearned })
    pinyin.learned = newLearned
    ElMessage.success(newLearned ? '已标记为已学' : '已标记为未学')
  } catch (error) {
    console.error('Failed to toggle learned status:', error)
    ElMessage.error('操作失败')
  }
}

const loadPinyinList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await pinyinApi.list(params)
    pinyinList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load pinyin list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadPinyinList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadPinyinList()
}

onMounted(() => {
  loadPinyinList()
})
</script>

<style scoped>
.pinyin-view {
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
  color: #FBBF24;
}

.pinyin-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .pinyin-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .pinyin-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .pinyin-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .pinyin-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.pinyin-item {
  display: flex;
}

.pinyin-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
  width: 100%;
}

.pinyin-card:hover {
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
  position: relative;
}

.pinyin-text {
  font-size: 3rem;
  font-weight: bold;
  margin: 0 0 8px 0;
  color: white;
}

.tone-tag {
  position: absolute;
  top: 12px;
  right: 12px;
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

.pinyin-example {
  text-align: center;
  width: 100%;
}

.example-label {
  margin: 0 0 8px 0;
  color: #909399;
  font-size: 0.9rem;
}

.example-text {
  margin: 0;
  color: #606266;
  font-size: 1.5rem;
  font-weight: bold;
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