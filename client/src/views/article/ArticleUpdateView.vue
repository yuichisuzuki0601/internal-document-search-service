<template>
  <div class="mx-0 px-10 py-10">
    <ArticleForm
      :article="article"
      ref="articleForm"
    />
    <v-text-field
      :value="datetimeFormat(article.createdAt)"
      disabled
      label="登録日時"
    />
    <v-text-field
      :value="datetimeFormat(article.updatedAt)"
      disabled
      label="更新日時"
    />
    <v-btn
      absolute
      color="primary"
      right
      @click="update"
    >
      <v-icon left>mdi-pencil</v-icon>
      <span>更新</span>
    </v-btn>
    <Loading :open="loadingOpen" />
    <Dialog
      :open="dialogOpen"
      color="success"
      title="成功"
      message="更新しました"
      @close="dialogClose"
    />
  </div>
</template>

<script>
import axios from 'axios';

import ArticleForm from '@/components/ArticleForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'ArticleUpdateView',
  components: {
    ArticleForm,
    Loading,
    Dialog
  },
  mixins: [
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    article: {
      code: '',
      content: '',
      result: '',
      createdAt: '',
      updatedAt: ''
    }
  }),
  computed: {
    articleId() {
      return this.$route.params.articleId;
    },
    articleForm() {
      return this.$refs.articleForm;
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: 'データ更新' });
    try {
      this.loadingOpen = true;
      const res = await axios.get(`/api/article/${this.articleId}`);
      this.article = res.data;
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async update() {
      if (!this.articleForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.put(`/api/article/${this.articleId}`, this.article);
        const status = res.status;
        if (status === 200) {
          this.article = res.data;
          this.dialogOpen = true;
          this.articleForm.resetValidation();
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