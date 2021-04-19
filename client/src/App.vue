<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="d-flex align-center">
        <router-link 
          class="px-2 pointer"
          tag="h2" 
          to="/"
        >
          {{ projectName }}
        </router-link>
        <h2 class="px-2">{{ pageTitle }}</h2>
      </div>
      <v-spacer />
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
    </v-app-bar>
    <v-navigation-drawer
      v-model="drawer"
      app
      bottom
      right
      temporary
    >
      <v-list nav dense>
        <v-list-item-group active-class="deep-purple--text text--accent-4">
          <v-list-item 
            v-for="({ icon, to, label }) in menus"
            :key="label"
            :to="to"
          >
            <v-list-item-icon>
              <v-icon>{{ icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{ label }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    drawer: false,
    menus: [{
      icon: 'mdi-magnify',
      to: '/',
      label: 'データ検索'
    },
    {
      icon: 'mdi-folder',
      to: '/scan-target-directory',
      label: '走査対象ディレクトリ管理'
    },
    {
      icon: 'mdi-file-word-box',
      to: '/compile-word-group',
      label: '翻訳ワード管理'
    },
    {
      icon: 'mdi-home',
      to: '/home',
      label: 'Home'
    }]
  }),
  computed: {
    projectName() {
      return document.title;
    },
    pageTitle() {
      return this.$store.getters.pageTitle;
    }
  },
};
</script>

<style scoped>
.pointer {
  cursor: pointer;
}
</style>