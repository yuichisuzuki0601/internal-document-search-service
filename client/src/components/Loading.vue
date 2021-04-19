<template>
  <v-overlay :value="open">
    <v-progress-circular
      indeterminate
      size="64"
    />
  </v-overlay>
</template>

<script>
  export default {
    name: 'Loading',
    props: [
      'open'
    ]
  }
</script>
