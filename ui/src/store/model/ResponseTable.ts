import { ResponseBase } from './ResponseBase';

export interface ResponseTable extends ResponseBase {
  data: any[][];
}
