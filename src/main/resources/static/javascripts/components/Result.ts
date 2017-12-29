import Vue from 'vue';
import Component from 'vue-class-component';
import store from '../store';
import ResultText from './ResultText.vue';
import ResultTable from './ResultTable.vue';

@Component({
  components: {
    ResultText,
    ResultTable,
  },
})
export default class Result extends Vue {

  // --- computed --------------------------------------------
  get results(): string {
    return store.getters.results;
  }
}
