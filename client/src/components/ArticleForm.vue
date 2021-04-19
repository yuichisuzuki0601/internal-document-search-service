<template>
  <v-form
    ref="form" 
    @submit.prevent
  >
    <v-text-field
      :rules="rules"
      :value="article.code"
      clearable
      clear-icon="mdi-close-circle"
      label="コード"
      @input="input('code', $event)"
    />
    <v-textarea
      :rules="rules"
      :value="article.content"
      clearable
      clear-icon="mdi-close-circle"
      label="内容"
      no-resize
      rows=10
      @input="input('content', $event)"
    />
    <v-text-field
      :value="article.result"
      clearable
      clear-icon="mdi-close-circle"
      label="結果"
      @input="input('result', $event)"
    />
  </v-form>
</template>

<script>
export default {
  name: 'ArticleForm',
  props: [
    'article'
  ],
  data: () => ({
    rules: [
      value => !!value || '必須入力欄です'
    ]
  }),
  computed: {
    form() {
      return this.$refs.form;
    }
  },
  methods: {
    input(itemName, value) {
      this.article[itemName] = value || '';
    },
    validate() {
      return this.form.validate();
    },
    resetValidation() {
      this.form.resetValidation();
    },
    reset() {
      this.form.reset();
    }
  }
}
</script>