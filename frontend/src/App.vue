<template>
  <el-container class="app-container">
    <el-header class="app-header">
      <div class="header-content">
        <div class="logo-section">
          <el-icon :size="32" class="logo-icon">
            <School />
          </el-icon>
          <h1 class="app-title">儿童学习乐园</h1>
        </div>
        <el-menu
        style="width:80%"
          :default-active="activeIndex"
          class="app-nav"
          mode="horizontal"
          router
          background-color="transparent"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/poetry">
            <el-icon><Collection /></el-icon>
            <span>诗词</span>
          </el-menu-item>
          <el-menu-item index="/character">
            <el-icon><EditPen /></el-icon>
            <span>识字</span>
          </el-menu-item>
          <el-menu-item index="/english">
            <el-icon><ChatLineRound /></el-icon>
            <span>英文</span>
          </el-menu-item>
          <el-menu-item index="/pinyin">
            <el-icon><Headset /></el-icon>
            <span>拼音</span>
          </el-menu-item>
          <el-menu-item index="/idiom">
            <el-icon><Medal /></el-icon>
            <span>成语</span>
          </el-menu-item>
          <el-menu-item index="/drawing">
            <el-icon><PictureFilled /></el-icon>
            <span>画画</span>
          </el-menu-item>
          <el-menu-item index="/math">
            <el-icon><Grid /></el-icon>
            <span>数学</span>
          </el-menu-item>
        </el-menu>
      </div>
    </el-header>
    <el-main class="app-main">
      <div class="main-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </el-main>
    <el-footer class="app-footer">
      <div class="footer-content">
        <p class="footer-text">© 2026 儿童学习乐园 - 快乐学习，健康成长</p>
      </div>
    </el-footer>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { 
  School, 
  HomeFilled, 
  Collection, 
  EditPen, 
  ChatLineRound, 
  Headset, 
  Medal, 
  PictureFilled, 
  Grid 
} from '@element-plus/icons-vue'

const route = useRoute()
const activeIndex = computed(() => route.path)
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.app-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e7ed 100%);
}

.app-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  position: sticky;
  top: 0;
  z-index: 1000;
  height: auto !important;
  min-height: 70px;
  padding: 10px 0;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 30px;
  height: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 30px;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-icon {
  color: #ffd04b;
}

.app-title {
  font-size: 1.6rem;
  font-weight: bold;
  margin: 0;
  background: linear-gradient(45deg, #fff, #ffd04b);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.app-nav {
  border-bottom: none !important;
  flex-wrap: wrap;
  height: auto !important;
  overflow: visible;
}

.app-nav .el-menu-item {
  font-size: 0.9rem;
  padding: 0 12px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.app-nav .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1) !important;
}

.app-nav .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.15) !important;
  border-bottom: 2px solid #ffd04b !important;
}

.app-main {
  padding: 30px;
  min-height: calc(100vh - 70px - 60px);
}

.main-content {
  max-width: 1400px;
  margin: 0 auto;
}

.app-footer {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  height: 60px !important;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-content {
  text-align: center;
}

.footer-text {
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.9rem;
  margin: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>