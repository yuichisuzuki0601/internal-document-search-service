<script>
import { format, parseISO } from 'date-fns';

export default {
  methods: {
    datetimeFormat(datetime) {
      return datetime ? format(parseISO(datetime), 'yyyy-MM-dd HH:mm:ss') : '';
    }
  }
}
</script>