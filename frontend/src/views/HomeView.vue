<template>
  <div class="home">
    <!-- 气泡背景 -->
    <div class="bubbles-container">
      <div v-for="n in 20" :key="n" class="bubble" :style="getBubbleStyle(n)"></div>
    </div>
    
    <div class="content-wrapper">
      <div class="welcome-section">
        <h2 class="welcome-title">欢迎来到儿童学习乐园</h2>
        <p class="welcome-subtitle">
          快乐学习，健康成长，开启智慧之旅
        </p>
      </div>
      <el-row :gutter="20" class="module-grid">
        <el-col 
          v-for="module in modules" 
          :key="module.id"
          :xs="24" 
          :sm="12" 
          :md="8" 
          :lg="6"
          class="module-col"
        >
          <el-card 
            class="module-card"
            @click="navigateTo(module.path)"
            :style="{ 
              background: `linear-gradient(135deg, ${module.bgColor} 0%, ${module.bgColorLight} 100%)`,
              border: `2px solid ${module.borderColor}`
            }"
          >
            <div class="card-content">
              <div class="module-header">
                <el-icon class="module-icon" :size="32" :color="module.titleColor">
                  <component :is="module.iconComponent" />
                </el-icon>
                <h3 class="module-title" :style="{ color: module.titleColor }">{{ module.name }}</h3>
              </div>
              <p class="module-description">{{ module.description }}</p>
              <div class="card-footer">
                <el-button 
                  :style="{ 
                    backgroundColor: module.btnColor, 
                    borderColor: module.btnColor,
                    color: '#fff'
                  }" 
                  size="small"
                  class="enter-btn"
                  round
                >
                  进入学习
                </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { 
  Collection, 
  EditPen, 
  ChatLineRound, 
  Headset, 
  Medal, 
  PictureFilled, 
  Grid 
} from '@element-plus/icons-vue'

const router = useRouter()

const modules = [
  {
    id: 1,
    name: '诗词',
    path: '/poetry',
    iconComponent: Collection,
    description: '学习经典诗词，感受中华文化之美',
    bgColor: '#FFF0F5',
    bgColorLight: '#FFE4E1',
    borderColor: '#FFB6C1',
    titleColor: '#DB7093',
    btnColor: '#FF69B4'
  },
  {
    id: 2,
    name: '识字',
    path: '/character',
    iconComponent: EditPen,
    description: '认识汉字，打好语文基础',
    bgColor: '#F0FFF0',
    bgColorLight: '#E0FFE0',
    borderColor: '#90EE90',
    titleColor: '#228B22',
    btnColor: '#32CD32'
  },
  {
    id: 3,
    name: '英文',
    path: '/english',
    iconComponent: ChatLineRound,
    description: '学习英语，拓展国际视野',
    bgColor: '#F0F8FF',
    bgColorLight: '#E6F3FF',
    borderColor: '#87CEEB',
    titleColor: '#1E90FF',
    btnColor: '#4169E1'
  },
  {
    id: 4,
    name: '拼音',
    path: '/pinyin',
    iconComponent: Headset,
    description: '学习拼音，掌握发音技巧',
    bgColor: '#FFFAF0',
    bgColorLight: '#FFF8DC',
    borderColor: '#FFD700',
    titleColor: '#FF8C00',
    btnColor: '#FFA500'
  },
  {
    id: 5,
    name: '成语',
    path: '/idiom',
    iconComponent: Medal,
    description: '学习成语，丰富语言表达',
    bgColor: '#FFF5EE',
    bgColorLight: '#FFE4E1',
    borderColor: '#FFA07A',
    titleColor: '#DC143C',
    btnColor: '#FF6347'
  },
  {
    id: 6,
    name: '画画',
    path: '/drawing',
    iconComponent: PictureFilled,
    description: '发挥创意，培养艺术细胞',
    bgColor: '#F8F0FF',
    bgColorLight: '#F0E6FF',
    borderColor: '#DDA0DD',
    titleColor: '#8A2BE2',
    btnColor: '#9370DB'
  },
  {
    id: 7,
    name: '数学',
    path: '/math',
    iconComponent: Grid,
    description: '学习数学，锻炼逻辑思维',
    bgColor: '#F0FFFF',
    bgColorLight: '#E0FFFF',
    borderColor: '#00CED1',
    titleColor: '#008B8B',
    btnColor: '#20B2AA'
  }
]

const navigateTo = (path) => {
  router.push(path)
}

// 生成气泡样式
const getBubbleStyle = (n) => {
  const size = Math.random() * 80 + 40 // 40-120px，更大的气泡
  const left = Math.random() * 100 // 0-100%
  const delay = Math.random() * 8 // 0-8s
  const duration = Math.random() * 8 + 12 // 12-20s
  const colors = [
    'rgba(255, 182, 193, 0.7)', // 粉色
    'rgba(144, 238, 144, 0.7)', // 绿色
    'rgba(135, 206, 235, 0.7)', // 蓝色
    'rgba(255, 215, 0, 0.7)',   // 黄色
    'rgba(255, 160, 122, 0.7)', // 橙色
    'rgba(221, 160, 221, 0.7)', // 紫色
    'rgba(0, 206, 209, 0.7)'    // 青色
  ]
  const color = colors[n % colors.length]
  
  return {
    width: `${size}px`,
    height: `${size}px`,
    left: `${left}%`,
    animationDelay: `${delay}s`,
    animationDuration: `${duration}s`,
    background: `radial-gradient(circle at 30% 30%, ${color}, ${color.replace('0.7', '0.3')}, transparent)`,
    boxShadow: `0 0 ${size}px ${color}, inset 0 0 ${size/2}px rgba(255,255,255,0.5)`
  }
}
</script>

<style scoped>
.home {
  position: relative;
  min-height: calc(100vh - 140px);
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
  overflow: hidden;
}

/* 气泡容器 */
.bubbles-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  pointer-events: none;
}

/* 气泡样式 */
.bubble {
  position: absolute;
  bottom: -150px;
  border-radius: 50%;
  animation: rise linear infinite;
  opacity: 0.8;
  border: 2px solid rgba(255, 255, 255, 0.3);
}

@keyframes rise {
  0% {
    transform: translateY(0) scale(1) rotate(0deg);
    opacity: 0.8;
  }
  25% {
    opacity: 1;
  }
  50% {
    transform: translateY(-60vh) scale(1.1) rotate(180deg);
    opacity: 1;
  }
  75% {
    opacity: 0.9;
  }
  100% {
    transform: translateY(-130vh) scale(0.6) rotate(360deg);
    opacity: 0;
  }
}

/* 内容包装器 */
.content-wrapper {
  position: relative;
  z-index: 1;
  padding: 20px 40px;
}

.welcome-section {
  text-align: center;
  margin-bottom: 40px;
  padding: 30px 0;
}

.welcome-title {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 12px;
  background: linear-gradient(135deg, #fff 0%, #ffd04b 50%, #fff 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 30px rgba(255, 255, 255, 0.5);
}

.welcome-subtitle {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.9);
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.module-grid {
  max-width: 1400px;
  margin: 0;
}

.module-col {
  margin-bottom: 20px;
}

.module-card {
  transition: all 0.3s ease;
  cursor: pointer;
  border-radius: 16px;
  overflow: hidden;
  backdrop-filter: blur(10px);
}

.module-card:hover {
  transform: translateY(-8px) scale(1.02);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.card-content {
  padding: 24px;
  text-align: center;
}

.module-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 12px;
}

.module-icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.module-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin: 0;
}

.module-description {
  color: #666;
  font-size: 0.95rem;
  line-height: 1.5;
  margin-bottom: 16px;
  min-height: 42px;
}

.card-footer {
  margin-top: 8px;
}

.enter-btn {
  border-radius: 20px;
  padding: 8px 20px;
  font-weight: bold;
  transition: all 0.3s ease;
}

.enter-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}
</style>