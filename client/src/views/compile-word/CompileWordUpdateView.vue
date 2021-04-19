<template>
  <div class="mx-0 px-10 py-10">
    <CompileWordForm
      :compileWord="compileWord"
      ref="compileWordForm"
    />
    <v-text-field
      :value="datetimeFormat(compileWord.createdAt)"
      disabled
      label="登録日時"
    />
    <v-text-field
      :value="datetimeFormat(compileWord.updatedAt)"
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

import CompileWordForm from '@/components/CompileWordForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'CompileWordUpdateView',
  components: {
    CompileWordForm,
    Loading,
    Dialog
  },
  mixins: [
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    compileWord: {
      compileWordGroupId: 0,
      compileBefore: '',
      compileAfter: '',
      createdAt: '',
      updatedAt: ''
    }
  }),
  computed: {
    compileWordGroupId() {
      return this.$route.params.compileWordGroupId;
    },
    compileWordId() {
      return this.$route.params.compileWordId;
    },
    compileWordForm() {
      return this.$refs.compileWordForm;
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '翻訳ワード更新' });
    try {
      this.loadingOpen = true;
      const res = await axios.get(`/api/compile-word/${this.compileWordId}`);
      this.compileWord = res.data;
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async update() {
      if (!this.compileWordForm.validate()) {
        return;
      }
      const obj = { ...this.compileWord };
      obj.compileAfter = this.compileWordForm.unformat(obj.compileAfter);
      try {
        this.loadingOpen = true;
        const res = await axios.put(`/api/compile-word/${this.compileWordId}`, obj);
        const status = res.status;
        if (status === 200) {
          this.compileWord = res.data;
          this.dialogOpen = true;
          this.compileWordForm.resetValidation();
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