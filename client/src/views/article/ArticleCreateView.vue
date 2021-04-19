<template>
  <div class="mx-0 px-10 py-10">
    <ArticleForm
      :article="article"
      ref="articleForm"
    />
    <v-btn
      absolute
      color="primary"
      right
      @click="create"
    >
      <v-icon left>mdi-plus</v-icon>
      <span>登録</span>
    </v-btn>
    <Loading :open="loadingOpen" />
    <Dialog
      :open="dialogOpen"
      color="success"
      title="成功"
      message="登録しました"
      @close="dialogClose"
    />
  </div>
</template>

<script>
import axios from 'axios';
import ArticleForm from '@/components/ArticleForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  name: 'ArticleCreateView',
  components: {
    ArticleForm,
    Loading,
    Dialog
  },
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    article: {
      code: '',
      content: '',
      result: ''
    }
  }),
  computed: {
    articleForm() {
      return this.$refs.articleForm;
    }
  },
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: 'データ登録' });
  },
  methods: {
    async create() {
      if (!this.articleForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.post('/api/article', this.article);
        const status = res.status;
        if (status === 200) {
          this.dialogOpen = true;
          this.articleForm.reset();
        }
      } catch(e) {
        console.log(e);
        alert(e);
      } finally {
        this.loadingOpen = false;
      }
    },
    dialogClose() {
      this.dialogOpen = false;
    }
  }
}
</script>