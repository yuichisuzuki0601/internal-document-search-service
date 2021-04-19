<template>
  <div class="mx-0 px-10 py-10">
    <CompileWordGroupForm
      :compileWordGroup="compileWordGroup"
      ref="compileWordGroupForm"
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
import CompileWordGroupForm from '@/components/CompileWordGroupForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  name: 'CompileWordGroupCreateView',
  components: {
    CompileWordGroupForm,
    Loading,
    Dialog
  },
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    compileWordGroup: {
      name: ''
    }
  }),
  computed: {
    compileWordGroupForm() {
      return this.$refs.compileWordGroupForm;
    }
  },
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '翻訳ワードグループ登録' });
  },
  methods: {
    async create() {
      if (!this.compileWordGroupForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.post('/api/compile-word-group', this.compileWordGroup);
        const status = res.status;
        if (status === 200) {
          this.dialogOpen = true;
          this.compileWordGroupForm.reset();
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