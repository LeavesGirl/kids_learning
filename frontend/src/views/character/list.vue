<template>
  <div class="character-view">
    <!-- 搜索区域 -->
    <el-card shadow="hover" class="search-card" :style="{ borderColor: '#90EE90' }">
      <el-form :model="searchForm" label-position="top" class="search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="汉字">
              <el-input
                v-model="searchForm.character"
                placeholder="请输入汉字"
                clearable
                :prefix-icon="Search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="拼音">
              <el-input
                v-model="searchForm.pinyin"
                placeholder="请输入拼音"
                clearable
                :prefix-icon="Document"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="4" :lg="3">
            <el-form-item label="类别">
              <el-select
                v-model="searchForm.category"
                placeholder="请选择类别"
                clearable
                style="width: 100%"
              >
                <el-option label="数字" value="数字" />
                <el-option label="自然" value="自然" />
                <el-option label="动物" value="动物" />
                <el-option label="植物" value="植物" />
                <el-option label="身体" value="身体" />
                <el-option label="颜色" value="颜色" />
                <el-option label="方位" value="方位" />
                <el-option label="时间" value="时间" />
                <el-option label="人称" value="人称" />
                <el-option label="动作" value="动作" />
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
                  :style="{ backgroundColor: '#32CD32', borderColor: '#32CD32', color: '#fff' }" 
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

    <!-- 汉字列表 -->
    <el-empty v-if="characterList.length === 0" description="暂无数据" :image-size="200" />
    
    <div v-else class="character-grid">
      <div
        v-for="character in characterList"
        :key="character.id"
        class="character-item"
      >
        <el-card
          class="character-card"
          @click="goToDetail(character.id)"
          shadow="hover"
          :body-style="{ padding: '0', height: '100%' }"
          :style="{ borderColor: '#90EE90' }"
        >
          <div class="card-wrapper">
            <div class="card-header" :style="{ background: 'linear-gradient(135deg, #F0FFF0 0%, #E0FFE0 100%)' }">
              <h3 class="character-text" :style="{ color: '#228B22' }">{{ character.character }}</h3>
              <p class="character-pinyin" :style="{ color: '#666' }">{{ character.pinyin }}</p>
            </div>
            <div class="card-body" :style="{ backgroundColor: '#F5FFFA' }">
              <div class="character-meaning">
                <p class="meaning-text">{{ character.meaning }}</p>
              </div>
            </div>
            <div class="card-footer">
              <el-button
                :style="character.learned ? { backgroundColor: '#52c41a', borderColor: '#52c41a', color: '#fff' } : { backgroundColor: '#ff4d4f', borderColor: '#ff4d4f', color: '#fff' }"
                size="small"
                @click.stop="toggleLearned(character)"
              >
                <el-icon><Check v-if="character.learned" /><CircleClose v-else /></el-icon>
                {{ character.learned ? '已学' : '未学' }}
              </el-button>
              <el-button
                :style="{ color: '#32CD32' }"
                size="small"
                text
                @click.stop="goToDetail(character.id)"
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
    <el-card v-if="characterList.length > 0" shadow="never" class="pagination-card" :style="{ borderColor: '#90EE90' }">
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
import { characterApi } from '@/api/character'
import { Search, RefreshRight, Document, Collection, ArrowRight, Check, CircleClose } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const searchForm = ref({
  character: '',
  pinyin: '',
  category: '',
  learned: null
})

const characterList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const goBack = () => {
  router.push('/')
}

const goToDetail = (id) => {
  router.push(`/character/detail/${id}`)
}

const search = () => {
  pageNum.value = 1
  loadCharacterList()
}

const reset = () => {
  searchForm.value = {
    character: '',
    pinyin: '',
    category: '',
    learned: null
  }
  pageNum.value = 1
  loadCharacterList()
}

const toggleLearned = async (character) => {
  try {
    const newLearned = !character.learned
    await characterApi.update(character.id, { id: character.id, learned: newLearned })
    character.learned = newLearned
    ElMessage.success(newLearned ? '已标记为已学' : '已标记为未学')
  } catch (error) {
    console.error('Failed to toggle learned:', error)
    ElMessage.error('操作失败')
  }
}

const loadCharacterList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...searchForm.value
    }
    const response = await characterApi.list(params)
    characterList.value = response.records
    total.value = response.total
  } catch (error) {
    console.error('Failed to load character list:', error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadCharacterList()
}

const handleCurrentChange = (current) => {
  pageNum.value = current
  loadCharacterList()
}

onMounted(() => {
  loadCharacterList()
})
</script>

<style scoped>
.character-view {
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
  color: #4ECDC4;
}

.character-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

@media (max-width: 1200px) {
  .character-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) {
  .character-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .character-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .character-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.character-item {
  display: flex;
}

.character-card {
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
  width: 100%;
}

.character-card:hover {
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

.character-text {
  font-size: 3rem;
  font-weight: bold;
  margin: 0 0 8px 0;
  color: white;
  font-family: 'STKaiti', 'KaiTi', 'SimKaiti', serif;
}

.character-pinyin {
  margin: 0;
  font-size: 1.1rem;
  opacity: 0.9;
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

.character-meaning {
  text-align: center;
  width: 100%;
}

.meaning-text {
  margin: 0;
  color: #606266;
  font-size: 1rem;
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