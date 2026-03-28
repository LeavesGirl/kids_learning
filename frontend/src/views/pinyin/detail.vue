<template>
  <div class="pinyin-detail">
    <div class="page-header">
      <el-page-header
        @back="goBack"
        content="拼音详情"
        class="page-header-content"
      />
    </div>

    <el-card v-if="pinyin" shadow="hover" class="detail-card">
      <div class="pinyin-header">
        <h2 class="pinyin-text">{{ pinyin.pinyin }}</h2>
        <el-tag size="large" effect="dark" class="type-tag">
          {{ pinyin.type === 'vowel' ? '元音' : '辅音' }}
        </el-tag>
      </div>

      <el-divider />

      <div class="pinyin-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="拼音">
            <span class="pinyin-text-large">{{ pinyin.pinyin }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="类型">
            <el-tag :type="pinyin.type === 'vowel' ? 'warning' : 'info'" effect="plain">
              {{ pinyin.type === 'vowel' ? '元音（韵母）' : '辅音（声母）' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="发音说明">
            {{ pinyin.description || '-' }}
          </el-descriptions-item>
        </el-descriptions>

        <!-- 四声展示区域（仅元音显示） -->
        <div v-if="pinyin.type === 'vowel'" class="tones-section">
          <h3 class="section-title">四声发音</h3>
          <div class="tones-grid">
            <div 
              v-for="(tone, index) in fourTones" 
              :key="index"
              class="tone-card"
              :class="`tone-${index + 1}`"
            >
              <div class="tone-number">{{ index + 1 }}</div>
              <div class="tone-pinyin">{{ tone.pinyin }}</div>
              <div class="tone-char">{{ tone.character }}</div>
              <div class="tone-name">{{ tone.name }}</div>
            </div>
          </div>
        </div>

        <!-- 发音技巧 -->
        <div class="tips-section">
          <h3 class="section-title">发音技巧</h3>
          <el-alert
            :title="pinyin.type === 'vowel' ? '元音发音要点' : '辅音发音要点'"
            :type="pinyin.type === 'vowel' ? 'warning' : 'info'"
            :description="getPronunciationTips()"
            show-icon
            :closable="false"
          />
        </div>
      </div>

      <div class="card-actions">
        <el-button size="large" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>
    </el-card>

    <el-empty v-else description="拼音不存在" class="empty-state" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { pinyinApi } from '@/api/pinyin'
import { ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const pinyin = ref(null)

// 四声数据
const fourTones = computed(() => {
  if (!pinyin.value || pinyin.value.type !== 'vowel') return []
  
  const basePinyin = pinyin.value.pinyin
  const baseChar = pinyin.value.character
  
  // 根据基础拼音生成四声
  const toneMap = {
    'a': ['ā', 'á', 'ǎ', 'à'],
    'o': ['ō', 'ó', 'ǒ', 'ò'],
    'e': ['ē', 'é', 'ě', 'è'],
    'i': ['ī', 'í', 'ǐ', 'ì'],
    'u': ['ū', 'ú', 'ǔ', 'ù'],
    'ü': ['ǖ', 'ǘ', 'ǚ', 'ǜ'],
    'ai': ['āi', 'ái', 'ǎi', 'ài'],
    'ei': ['ēi', 'éi', 'ěi', 'èi'],
    'ui': ['uī', 'uí', 'uǐ', 'uì'],
    'ao': ['āo', 'áo', 'ǎo', 'ào'],
    'ou': ['ōu', 'óu', 'ǒu', 'òu'],
    'iu': ['iū', 'iú', 'iǔ', 'iù'],
    'ie': ['iē', 'ié', 'iě', 'iè'],
    'üe': ['üē', 'üé', 'üě', 'üè'],
    'er': ['ēr', 'ér', 'ěr', 'èr'],
    'an': ['ān', 'án', 'ǎn', 'àn'],
    'en': ['ēn', 'én', 'ěn', 'èn'],
    'in': ['īn', 'ín', 'ǐn', 'ìn'],
    'un': ['ūn', 'ún', 'ǔn', 'ùn'],
    'ün': ['ǖn', 'ǘn', 'ǚn', 'ǜn'],
    'ang': ['āng', 'áng', 'ǎng', 'àng'],
    'eng': ['ēng', 'éng', 'ěng', 'èng'],
    'ing': ['īng', 'íng', 'ǐng', 'ìng'],
    'ong': ['ōng', 'óng', 'ǒng', 'òng']
  }
  
  const toneNames = ['第一声（阴平）', '第二声（阳平）', '第三声（上声）', '第四声（去声）']
  const toneChars = {
    'a': ['阿', '啊', '啊', '啊'],
    'o': ['喔', '哦', '哦', '哦'],
    'e': ['婀', '俄', '恶', '饿'],
    'i': ['衣', '移', '以', '意'],
    'u': ['乌', '无', '五', '务'],
    'ü': ['迂', '于', '与', '玉'],
    'ai': ['哀', '捱', '矮', '爱'],
    'ei': ['诶', '诶', '诶', '诶'],
    'ui': ['威', '围', '伟', '卫'],
    'ao': ['凹', '敖', '袄', '傲'],
    'ou': ['欧', '侯', '偶', '沤'],
    'iu': ['优', '由', '有', '又'],
    'ie': ['耶', '爷', '也', '夜'],
    'üe': ['约', '月', '哕', '悦'],
    'er': ['儿', '而', '耳', '二'],
    'an': ['安', '鞍', '俺', '岸'],
    'en': ['恩', '嗯', '嗯', '嗯'],
    'in': ['因', '银', '引', '印'],
    'un': ['温', '文', '稳', '问'],
    'ün': ['晕', '云', '允', '运'],
    'ang': ['肮', '昂', '盎', '盎'],
    'eng': ['鞥', '嗯', '嗯', '嗯'],
    'ing': ['英', '迎', '影', '映'],
    'ong': ['翁', '嗡', '蓊', '瓮']
  }
  
  const tones = toneMap[basePinyin] || ['', '', '', '']
  const chars = toneChars[basePinyin] || [baseChar, baseChar, baseChar, baseChar]
  
  return tones.map((tonePinyin, index) => ({
    pinyin: tonePinyin,
    character: chars[index],
    name: toneNames[index]
  }))
})

const goBack = () => {
  router.push('/pinyin')
}

const getPronunciationTips = () => {
  if (!pinyin.value) return ''
  
  if (pinyin.value.type === 'vowel') {
    return '元音发音时要注意口型的变化，第一声高平，第二声上扬，第三声降升，第四声下降。发音要饱满，声音要响亮。'
  } else {
    return '辅音发音时要注意发音部位和发音方法。双唇音用双唇，舌尖音用舌尖，舌根音用舌根。发音要清晰有力。'
  }
}

const loadPinyinDetail = async () => {
  try {
    const id = route.params.id
    const response = await pinyinApi.detail(id)
    pinyin.value = response
  } catch (error) {
    console.error('Failed to load pinyin detail:', error)
  }
}

onMounted(() => {
  loadPinyinDetail()
})
</script>

<style scoped>
.pinyin-detail {
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
  border: 2px solid #FFD700;
}

.pinyin-header {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #FFFAF0 0%, #FFF8DC 100%);
  margin: -20px -20px 0 -20px;
  position: relative;
}

.pinyin-text {
  font-size: 5rem;
  font-weight: bold;
  margin: 0 0 10px 0;
  color: #FF8C00;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.type-tag {
  position: absolute;
  top: 20px;
  right: 20px;
  background-color: #FFA500 !important;
  border-color: #FFA500 !important;
}

.pinyin-content {
  padding: 30px 20px;
}

:deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
  background: linear-gradient(135deg, #FFFAF0 0%, #FFF8DC 100%);
  color: #FF8C00;
}

:deep(.el-descriptions__content) {
  background-color: #fff;
}

.pinyin-text-large {
  font-size: 1.5rem;
  font-weight: bold;
  color: #FF8C00;
}

/* 四声展示区域 */
.tones-section {
  margin-top: 30px;
}

.section-title {
  font-size: 1.2rem;
  font-weight: bold;
  color: #FF8C00;
  margin-bottom: 20px;
  padding-left: 10px;
  border-left: 4px solid #FFA500;
}

.tones-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 30px;
}

@media (max-width: 768px) {
  .tones-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

.tone-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.tone-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.tone-1 {
  border-color: #52c41a;
  background: linear-gradient(135deg, #f6ffed 0%, #ffffff 100%);
}

.tone-2 {
  border-color: #1890ff;
  background: linear-gradient(135deg, #e6f7ff 0%, #ffffff 100%);
}

.tone-3 {
  border-color: #faad14;
  background: linear-gradient(135deg, #fffbe6 0%, #ffffff 100%);
}

.tone-4 {
  border-color: #f5222d;
  background: linear-gradient(135deg, #fff1f0 0%, #ffffff 100%);
}

.tone-number {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background: #FFA500;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin: 0 auto 10px;
}

.tone-pinyin {
  font-size: 2rem;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.tone-char {
  font-size: 1.5rem;
  color: #FF8C00;
  font-weight: bold;
  margin-bottom: 5px;
}

.tone-name {
  font-size: 0.85rem;
  color: #909399;
}

/* 发音技巧区域 */
.tips-section {
  margin-top: 30px;
}

.card-actions {
  padding: 20px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #FFD700;
}

.back-btn {
  border-radius: 25px;
  padding: 12px 30px;
  font-size: 1rem;
  background-color: #FFA500;
  border-color: #FFA500;
  color: #fff;
}

.back-btn:hover {
  background-color: #FF8C00;
  border-color: #FF8C00;
}

.empty-state {
  padding: 60px 0;
}
</style>