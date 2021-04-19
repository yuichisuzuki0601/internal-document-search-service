<template>
  <v-dialog
    :value="open"
    max-width="600"
    transition="dialog-bottom-transition"
    @close="close"
    @keydown.enter="close"
  >
    <v-card>
      <v-toolbar
        :color="color"
        dark
      >
        {{ title }}
      </v-toolbar>
      <v-card-text>
        <div class="text-h2 pa-12">{{ message }}</div>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn
          text 
          @click.stop="close"
        >
          <span>閉じる</span>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
  export default {
    name: 'Dialog',
    props: {
      open: {
        type: Boolean
      },
      color: {
        // primary, secondary, accent, error, info, success, warning
        type: String,
        default: 'primary'
      },
      title: {
        type: String
      },
      message: {
        type: String
      }
    },
    methods: {
      close() {
        this.$emit('close');
      }
    }
  }
</script>