<template>
  <v-form
    ref="form" 
    @submit.prevent
  >
    <v-text-field
      :rules="rules"
      :value="scanTargetDirectory.name"
      clearable
      clear-icon="mdi-close-circle"
      label="名前"
      @input="input('name', $event)"
    />
    <v-text-field
      :rules="rules"
      :value="scanTargetDirectory.path"
      clearable
      clear-icon="mdi-close-circle"
      label="パス"
      @input="input('path', $event)"
    />
    <v-text-field
      :rules="rules"
      :value="scanTargetDirectory.extension"
      clearable
      clear-icon="mdi-close-circle"
      label="拡張子"
      @input="input('extension', $event)"
    />
  </v-form>
</template>

<script>
export default {
  name: 'ScanTargetDirectoryForm',
  props: [
    'scanTargetDirectory'
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
      this.scanTargetDirectory[itemName] = value || '';
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