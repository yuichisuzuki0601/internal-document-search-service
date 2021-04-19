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
                color="primary"
                to="/compile-word-group/create"
              >
                <v-icon left>mdi-plus</v-icon>
                <span>翻訳ワードグループ追加</span>
              </v-btn>
            </v-col>
            <v-col
              cols="12"
              sm="3"
            >
              <v-btn
                :disabled="selected.length === 0"
                color="error"
                @click="deleteCompileWordGroup"
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
          :to="`compile-word-group/${item.id}/update`"
          color="primary"
          text
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </template>
      <template v-slot:[`item.list`]="{item}">
        <v-btn
          :to="`compile-word-group/${item.id}/compile-word`"
          color="primary"
          text
        >
          <v-icon>mdi-format-list-bulleted</v-icon>
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
  name: 'CompileWordGroupListView',
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
    selected: [],
  }),
  computed: {
    headers() {
      return [
        { text: '', value: 'edit', sortable: false, width: 80 },
        { text: '翻訳ワード一覧', value: 'list', sortable: false, width: 120 },
        { text: '名前', value: 'name', cellClass: 'ellipsis' },
        { text: '登録日時', value: 'createdAt', cellClass: 'ellipsis' },
        { text: '更新日時', value: 'updatedAt', cellClass: 'ellipsis' }
      ]
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '翻訳ワードグループ一覧' });
    try {
      this.loadingOpen = true;
      const res = await axios.get("/api/compile-word-group");
      this.result = res.data;
      this.selected = [];
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async deleteCompileWordGroup() {
      const ids = this.selected.map(compileWordGroup => compileWordGroup.id);
      try {
        this.loadingOpen = true;
        const res = await axios.delete(`/api/compile-word-group?ids=${ids}`);
        this.selected = [];
        this.deleteSuccessDialogOpen = true;
        const deletedIds = res.data;
        this.result = this.result.filter(compileWordGroup => !deletedIds.includes(compileWordGroup.id));
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