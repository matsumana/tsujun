import Vue from 'vue';
import Component from 'vue-class-component';
import { Prop } from 'vue-property-decorator';
import { ACTION } from '../store/action-types';
import store from '../store';

@Component
export default class ResultTable extends Vue {
  @Prop()
  readonly sequence: number;

  @Prop()
  readonly sql: string;

  @Prop()
  readonly mode: number;

  @Prop()
  readonly data: any[][];

  // --- method ----------------------------------------------
  cancel(event: Event) {
    const id: number = Number(event.srcElement.id.split('-')[1]);
    store.dispatch(ACTION.CANCEL, id);
  }
}
