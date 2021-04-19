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
                to="/scan-target-directory/create"
              >
                <v-icon left>mdi-plus</v-icon>
                <span>走査対象ディレクトリ追加</span>
              </v-btn>
            </v-col>
            <v-col
              cols="12"
              sm="3"
            >
              <v-btn
                :disabled="selected.length === 0"
                color="error"
                @click="deleteScanTargetDirectory"
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
          :to="`scan-target-directory/${item.id}/update`"
          color="primary"
          text
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
  name: 'ScanTargetDirectoryListView',
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
        { text: '名前', value: 'name', cellClass: 'ellipsis' },
        { text: 'パス', value: 'path', cellClass: 'ellipsis' },
        { text: '拡張子', value: 'extension', cellClass: 'ellipsis' },
        { text: '登録日時', value: 'createdAt', cellClass: 'ellipsis' },
        { text: '更新日時', value: 'updatedAt', cellClass: 'ellipsis' }
      ]
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '走査対象ディレクトリ一覧' });
    try {
      this.loadingOpen = true;
      const res = await axios.get("/api/scan-target-directory");
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
    async deleteScanTargetDirectory() {
      const ids = this.selected.map(scanTargetDirectory => scanTargetDirectory.id);
      try {
        this.loadingOpen = true;
        const res = await axios.delete(`/api/scan-target-directory?ids=${ids}`);
        this.selected = [];
        this.deleteSuccessDialogOpen = true;
        const deletedIds = res.data;
        this.result = this.result.filter(scanTargetDirectory => !deletedIds.includes(scanTargetDirectory.id));
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