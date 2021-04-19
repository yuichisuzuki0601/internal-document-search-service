<template>
  <div class="mx-0 px-10 py-10">
    <ScanTargetDirectoryForm
      :scanTargetDirectory="scanTargetDirectory"
      ref="scanTargetDirectoryForm"
    />
    <v-text-field
      :value="datetimeFormat(scanTargetDirectory.createdAt)"
      disabled
      label="登録日時"
    />
    <v-text-field
      :value="datetimeFormat(scanTargetDirectory.updatedAt)"
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

import ScanTargetDirectoryForm from '@/components/ScanTargetDirectoryForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'ScanTargetDirectoryUpdateView',
  components: {
    ScanTargetDirectoryForm,
    Loading,
    Dialog
  },
  mixins: [
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    scanTargetDirectory: {
      name: '',
      path: '',
      extension: '',
      createdAt: '',
      updatedAt: ''
    }
  }),
  computed: {
    scanTargetDirectoryId() {
      return this.$route.params.scanTargetDirectoryId;
    },
    scanTargetDirectoryForm() {
      return this.$refs.scanTargetDirectoryForm;
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '走査対象ディレクトリ更新' });
    try {
      this.loadingOpen = true;
      const res = await axios.get(`/api/scan-target-directory/${this.scanTargetDirectoryId}`);
      this.scanTargetDirectory = res.data;
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async update() {
      if (!this.scanTargetDirectoryForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.put(`/api/scan-target-directory/${this.scanTargetDirectoryId}`, this.scanTargetDirectory);
        const status = res.status;
        if (status === 200) {
          this.scanTargetDirectory = res.data;
          this.dialogOpen = true;
          this.scanTargetDirectoryForm.resetValidation();
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