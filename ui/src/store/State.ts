import { ResponseBase } from './model/ResponseBase';

export class State {
  sequence = 0;
  sql = '';
  results: ResponseBase[] = [];
}
