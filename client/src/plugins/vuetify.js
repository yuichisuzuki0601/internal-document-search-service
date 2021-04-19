import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import ja from 'vuetify/es5/locale/ja';

Vue.use(Vuetify);

export default new Vuetify({
  lang: {
    locales: { ja },
    current: 'ja'
  }
});
