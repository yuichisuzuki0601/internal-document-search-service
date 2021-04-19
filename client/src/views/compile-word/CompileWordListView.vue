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
              sm="3"
            >
              <v-btn
                :to="`/compile-word-group/${compileWordGroupId}/compile-word/create`"
                color="primary"
              >
                <v-icon left>mdi-plus</v-icon>
                <span>翻訳ワード追加</span>
              </v-btn>
            </v-col>
            <v-col
              cols="12"
              sm="3"
            >
              <v-btn
                :disabled="selected.length === 0"
                color="error"
                @click="deleteCompileWord"
              >
                <v-icon left>mdi-delete</v-icon>
                <span>選択項目を削除</span>
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </template>
      <template v-slot:[`item.edit`]="{item}">
        <v-btn
          color="primary"
          text
          :to="`/compile-word-group/${compileWordGroupId}/compile-word/${item.id}/update`"
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
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
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'CompileWordListView',
  components: {
    Loading,
    Dialog
  },
  mixins: [
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    deleteSuccessDialogOpen: false,
    result: [],
    selected: []
  }),
  computed: {
    headers() {
      return [
        { text: '', value: 'edit', sortable: false, width: 80 },
        { text: '翻訳前', value: 'compileBefore', cellClass: 'ellipsis' },
        { text: '翻訳後', value: 'compileAfter', cellClass: 'ellipsis' },
        { text: '登録日時', value: 'createdAt', cellClass: 'ellipsis' },
        { text: '更新日時', value: 'updatedAt', cellClass: 'ellipsis' }
      ]
    },
    compileWordGroupId() {
      return this.$route.params.compileWordGroupId;
    }
  },
  async created() {
    try {
      this.loadingOpen = true;
      const res1 = await axios.get(`/api/compile-word-group/${this.compileWordGroupId}`);
      this.$store.dispatch('setPageTitle', { pageTitle: `翻訳ワード一覧 (${res1.data.name})` });
      const res2 = await axios.get(`/api/compile-word?compileWordGroupId=${this.compileWordGroupId}`);
      this.result = res2.data;
      this.selected = [];
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async deleteCompileWord() {
      const ids = this.selected.map(compileWord => compileWord.id);
      try {
        this.loadingOpen = true;
        const res = await axios.delete(`/api/compile-word?ids=${ids}`);
        this.selected = [];
        this.deleteSuccessDialogOpen = true;
        const deletedIds = res.data;
        this.result = this.result.filter(compileWord => !deletedIds.includes(compileWord.id));
      } catch(e) {
        console.log(e);
        alert(e);
      } finally {
        this.loadingOpen = false;
      }
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
</style>