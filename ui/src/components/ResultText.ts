import Vue from 'vue';
import Component from 'vue-class-component';
import { Prop } from 'vue-property-decorator';

@Component
export default class ResultText extends Vue {
  @Prop()
  readonly sequence: number;

  @Prop()
  readonly sql: string;

  @Prop()
  readonly mode: number;

  @Prop()
  readonly text: string;
}
