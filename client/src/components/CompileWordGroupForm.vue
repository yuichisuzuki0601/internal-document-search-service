<template>
  <v-form
    ref="form" 
    @submit.prevent
  >
    <v-text-field
      :rules="rules"
      :value="compileWordGroup.name"
      clearable
      clear-icon="mdi-close-circle"
      label="名前"
      @input="input('name', $event)"
    />
  </v-form>
</template>

<script>
export default {
  name: 'CompileWordGroupForm',
  props: [
    'compileWordGroup'
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
      this.compileWordGroup[itemName] = value || '';
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