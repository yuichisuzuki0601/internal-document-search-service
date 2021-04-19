<template>
  <v-form
    ref="form" 
    @submit.prevent
  >
    <v-text-field
      v-model="compileWord.compileBefore"
      :rules="rules"
      clearable
      clear-icon="mdi-close-circle"
      label="翻訳前"
      @input="input('compileBefore', $event)"
    />
    <v-textarea
      v-model="compileWord.compileAfter"
      :rules="rules"
      clearable
      clear-icon="mdi-close-circle"
      label="翻訳後(改行毎にOR検索となります)"
      no-resize
      rows=10
      @input="input('compileAfter', $event)"
    />
  </v-form>
</template>

<script>
export default {
  name: 'CompileWordForm',
  props: [
    'compileWord'
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
  watch: {
    compileWord(newCompileWord) {
      newCompileWord.compileAfter = this.format(newCompileWord.compileAfter);
    }
  },
  methods: {
    format(value) {
      return value.replace(/\|/g, '\n');
    },
    unformat(value) {
      return value.replace(/\n/g, '|').replace(/(\|){2,}/g, '|').replace(/\s+/g, '');
    },
    input(itemName, value) {
      if (value.includes('|')) {
        const oldValue = value.replace(/\|/g, '');
        this.$nextTick(() => this.compileWord[itemName] = oldValue || '');
        return;
      } else {
        this.compileWord[itemName] = value || '';
      }
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