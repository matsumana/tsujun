import Vue from 'vue';
import Component from 'vue-class-component';
import Brace from 'vue-bulma-brace/src/Brace.vue';
import { ACTION } from '../store/action-types';
import store from '../store';

@Component({
  components: {
    Brace,
  },
})
export default class Editor extends Vue {

  // --- input field -----------------------------------------
  sql = '';

  // --- method ----------------------------------------------
  oncodeChange(sql: string) {
    this.sql = sql;
    store.dispatch(ACTION.INPUT_SQL, this.sql);
  }

  // cancel() {
  //   this.sql = '';
  //   store.dispatch(ACTION.CANCEL, this.sql);
  // }

  submit() {
    store.dispatch(ACTION.SUBMIT);
  }
}
