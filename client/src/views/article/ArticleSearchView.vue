<template>
  <div class="mx-0 px-1 py-5">
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="result"
      class="elevation-0"
      item-key="id"
      show-select
    >
      <template v-slot:top>
        <v-container>
          <v-row
            align="center"
            no-gutters
          >
            <v-col
              cols="12"
              sm="2"
            >
              <v-btn
                color="primary"
                to="/create"
              >
                <v-icon left>mdi-plus</v-icon>
                <span>データ追加</span>
              </v-btn>
            </v-col>
            <v-col
              cols="12"
              sm="2"
            >
              <v-btn
                :disabled="selected.length === 0"
                color="error"
                @click="deleteArticle"
              >
                <v-icon left>mdi-delete</v-icon>
                <span>選択項目を削除</span>
              </v-btn>
            </v-col>
            <v-col
              cols="12"
              sm="8"
            >
              <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                class="mx-4"
                clearable
                clear-icon="mdi-close-circle"
                label="検索"
                ref="inputSearch"
                @compositionstart="composing=true"
                @compositionend="composing=false"
                @keydown.enter="doSearch"
                @click:append="doSearch"
              />
            </v-col>
          </v-row>
        </v-container>
      </template>
      <template v-slot:[`item.edit`]="{item}">
        <v-btn
          :to="`/${item.id}/update`"
          color="primary"
          text
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </template>
      <template v-slot:[`item.content`]="{item}">
        <span v-html="removeBr(item.content)" />
      </template>
      <template v-slot:[`item.createdAt`]="{item}">
        {{ datetimeFormat(item.createdAt) }}
      </template>
      <template v-slot:[`item.updatedAt`]="{item}">
        {{ datetimeFormat(item.updatedAt) }}
      </template>
    </v-data-table>
    <Loading :open="loadingOpen" />
    <Dialog
      :open="zeroResultDialogOpen"
      color="warning"
      title="結果"
      message="結果は0件でした"
      @close="zeroResultDialogClose"
    />
    <Dialog
      :open="deleteSuccessDialogOpen"
      color="success"
      title="成功"
      message="削除しました"
      @close="deleteSuccessDialogClose"
    />
  </div>
</template>

<script>
import axios from 'axios';
// @ is an alias to /src
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import HtmlProcessor from '@/mixins/HtmlProcessor.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'ArticleSearchView',
  components: {
    Loading,
    Dialog
  },
  mixins: [
    HtmlProcessor,
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    zeroResultDialogOpen: false,
    deleteSuccessDialogOpen: false,
    composing: false,
    search: '',
    result: [],
    selected: []
  }),
  computed: {
    headers() {
      return [
        { text: '', value: 'edit', sortable: false, width: 80 },
        { text: 'コード', value: 'code', cellClass: 'ellipsis' },
        { text: '内容', value: 'content', width: '30%', cellClass: 'ellipsis' },
        { text: '結果', value: 'result', cellClass: 'ellipsis' },
        { text: '登録日時', value: 'createdAt', cellClass: 'ellipsis' },
        { text: '更新日時', value: 'updatedAt', cellClass: 'ellipsis' }
      ]
    }
  },
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: 'データ検索' });
    this.search = localStorage.getItem('search') || '';
  },
  mounted() {
    this.$refs.inputSearch.focus();
  },
  methods: {
    async doSearch() {
      if (this.composing) return;
      this.search = this.search || '';
      localStorage.setItem('search', this.search);
      try {
        this.loadingOpen = true;
        const res = await axios.get(`/api/article/search?query=${this.search}`);
        this.result = res.data;
        this.selected = [];
        if (this.result.length === 0) this.zeroResultDialogOpen = true;
      } catch(e) {
        console.log(e);
        alert(e);
      } finally {
        this.loadingOpen = false;
      }
    },
    async deleteArticle() {
      const ids = this.selected.map(article => article.id);
      try {
        this.loadingOpen = true;
        const res = await axios.delete(`/api/article?ids=${ids}`);
        this.selected = [];
        this.deleteSuccessDialogOpen = true;
        const deletedIds = res.data;
        this.result = this.result.filter(article => !deletedIds.includes(article.id));
      } catch(e) {
        console.log(e);
        alert(e);
      } finally {
        this.loadingOpen = false;
      }
    },
    zeroResultDialogClose() {
      this.zeroResultDialogOpen = false;
    },
    deleteSuccessDialogClose() {
      this.deleteSuccessDialogOpen = false;
    }
  }
}
</script>

<style scoped>
::v-deep .v-data-table table {
  table-layout: fixed;
}
::v-deep .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
::v-deep em {
  font-style: normal;
  font-weight: bold;
}
</style>