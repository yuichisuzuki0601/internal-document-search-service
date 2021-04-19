<template>
  <div class="mx-0 px-10 py-10">
    <ScanTargetDirectoryForm
      :scanTargetDirectory="scanTargetDirectory"
      ref="scanTargetDirectoryForm"
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
import ScanTargetDirectoryForm from '@/components/ScanTargetDirectoryForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  name: 'ScanTargetDirectoryCreateView',
  components: {
    ScanTargetDirectoryForm,
    Loading,
    Dialog
  },
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    scanTargetDirectory: {
      name: '',
      path: '',
      extension: ''
    }
  }),
  computed: {
    scanTargetDirectoryForm() {
      return this.$refs.scanTargetDirectoryForm;
    }
  },
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '走査対象ディレクトリ登録' });
  },
  methods: {
    async create() {
      if (!this.scanTargetDirectoryForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.post('/api/scan-target-directory', this.scanTargetDirectory);
        const status = res.status;
        if (status === 200) {
          this.dialogOpen = true;
          this.scanTargetDirectoryForm.reset();
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