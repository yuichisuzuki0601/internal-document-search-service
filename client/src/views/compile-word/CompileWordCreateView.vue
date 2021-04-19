<template>
  <div class="mx-0 px-10 py-10">
    <CompileWordForm
      :compileWord="compileWord"
      ref="compileWordForm"
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
import CompileWordForm from '@/components/CompileWordForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  name: 'CompileWordCreateView',
  components: {
    CompileWordForm,
    Loading,
    Dialog
  },
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    compileWord: {
      compileWordGroupId: 0,
      compileBefore: '',
      compileAfter: ''
    }
  }),
  computed: {
    compileWordGroupId() {
      return this.$route.params.compileWordGroupId;
    },
    compileWordForm() {
      return this.$refs.compileWordForm;
    }
  },
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '翻訳ワード登録' });
    this.compileWord.compileWordGroupId = this.compileWordGroupId;
  },
  methods: {
    async create() {
      if (!this.compileWordForm.validate()) {
        return;
      }
      const obj = { ...this.compileWord };
      obj.compileAfter = this.compileWordForm.unformat(obj.compileAfter);
      try {
        this.loadingOpen = true;
        const res = await axios.post('/api/compile-word', obj);
        const status = res.status;
        if (status === 200) {
          this.dialogOpen = true;
          this.compileWordForm.reset();
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