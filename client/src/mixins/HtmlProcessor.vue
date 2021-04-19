<script>
export default {
  methods: {
    removeBr(html) {
      return html.replace(/<br \/>/g, ' ');
    }
  }
}
</script>