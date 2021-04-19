import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'ArticleSearchView',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "article-search-view" */ '../views/article/ArticleSearchView.vue')
  },
  {
    path: '/create',
    name: 'ArticleCreateView',
    component: () => import(/* webpackChunkName: "article-create-view" */ '../views/article/ArticleCreateView.vue')
  },
  {
    path: '/:articleId/update',
    name: 'ArticleUpdateView',
    component: () => import(/* webpackChunkName: "article-update-view" */ '../views/article/ArticleUpdateView.vue')
  },
  {
    path: '/scan-target-directory',
    name: 'ScanTargetDirectoryListView',
    component: () => import(/* webpackChunkName: "scan-target-directory-list-view" */ '../views/scan-target-directory/ScanTargetDirectoryListView.vue')
  },
  {
    path: '/scan-target-directory/create',
    name: 'ScanTargetDirectoryCreateView',
    component: () => import(/* webpackChunkName: "scan-target-directory-create-view" */ '../views/scan-target-directory/ScanTargetDirectoryCreateView.vue')
  },
  {
    path: '/scan-target-directory/:scanTargetDirectoryId/update',
    name: 'ScanTargetDirectoryUpdateView',
    component: () => import(/* webpackChunkName: "scan-target-directory-update-view" */ '../views/scan-target-directory/ScanTargetDirectoryUpdateView.vue')
  },
  {
    path: '/compile-word-group',
    name: 'CompileWordGroupListView',
    component: () => import(/* webpackChunkName: "compile-word-group-list-view" */ '../views/compile-word-group/CompileWordGroupListView.vue')
  },
  {
    path: '/compile-word-group/create',
    name: 'CompileWordGroupCreateView',
    component: () => import(/* webpackChunkName: "compile-word-group-create-view" */ '../views/compile-word-group/CompileWordGroupCreateView.vue')
  },
  {
    path: '/compile-word-group/:compileWordGroupId/update',
    name: 'CompileWordGroupUpdateView',
    component: () => import(/* webpackChunkName: "compile-word-group-update-view" */ '../views/compile-word-group/CompileWordGroupUpdateView.vue')
  },
  {
    path: '/compile-word-group/:compileWordGroupId/compile-word',
    name: 'CompileWordListView',
    component: () => import(/* webpackChunkName: "compile-word-list-view" */ '../views/compile-word/CompileWordListView.vue')
  },
  {
    path: '/compile-word-group/:compileWordGroupId/compile-word/create',
    name: 'CompileWordCreateView',
    component: () => import(/* webpackChunkName: "compile-word-create-view" */ '../views/compile-word/CompileWordCreateView.vue')
  },
  {
    path: '/compile-word-group/:compileWordGroupId/compile-word/:compileWordId/update',
    name: 'CompileWordUpdateView',
    component: () => import(/* webpackChunkName: "compile-word-update-view" */ '../views/compile-word/CompileWordUpdateView.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
