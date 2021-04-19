<template>
  <div class="mx-0 px-10 py-10">
    <CompileWordGroupForm
      :compileWordGroup="compileWordGroup"
      ref="compileWordGroupForm"
    />
    <v-text-field
      :value="datetimeFormat(compileWordGroup.createdAt)"
      disabled
      label="登録日時"
    />
    <v-text-field
      :value="datetimeFormat(compileWordGroup.updatedAt)"
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

import CompileWordGroupForm from '@/components/CompileWordGroupForm.vue';
import Loading from '@/components/Loading.vue';
import Dialog from '@/components/Dialog.vue';
import DatetimeFormatter from '@/mixins/DatetimeFormatter.vue';

export default {
  name: 'CompileWordGroupUpdateView',
  components: {
    CompileWordGroupForm,
    Loading,
    Dialog
  },
  mixins: [
    DatetimeFormatter
  ],
  data: () => ({
    loadingOpen: false,
    dialogOpen: false,
    compileWordGroup: {
      name: '',
      createdAt: '',
      updatedAt: ''
    }
  }),
  computed: {
    compileWordGroupId() {
      return this.$route.params.compileWordGroupId;
    },
    compileWordGroupForm() {
      return this.$refs.compileWordGroupForm;
    }
  },
  async created() {
    this.$store.dispatch('setPageTitle', { pageTitle: '翻訳ワードグループ更新' });
    try {
      this.loadingOpen = true;
      const res = await axios.get(`/api/compile-word-group/${this.compileWordGroupId}`);
      this.compileWordGroup = res.data;
    } catch(e) {
      console.log(e);
      alert(e);
    } finally {
      this.loadingOpen = false;
    }
  },
  methods: {
    async update() {
      if (!this.compileWordGroupForm.validate()) {
        return;
      }
      try {
        this.loadingOpen = true;
        const res = await axios.put(`/api/compile-word-group/${this.compileWordGroupId}`, this.compileWordGroup);
        const status = res.status;
        if (status === 200) {
          this.compileWordGroup = res.data;
          this.dialogOpen = true;
          this.compileWordGroupForm.resetValidation();
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