import { ActionContext } from 'vuex';
import { State } from '../store/State';
import { ACTION } from '../store/action-types';
import { Request as Req } from '../store/model/Request';

const WS_URL = `ws://${window.location.host}/query`;

export class Api {

  private ws: WebSocket;
  private store: ActionContext<State, State>;

  constructor() {
    this.initWs();
  }

  private initWs() {
    this.ws = new WebSocket(WS_URL);

    this.ws.onopen = () => {
      console.log(`WebSocket open ${WS_URL}`);
    };

    this.ws.onerror = (ev: Event) => {
      console.error(`WebSocket error ${ev}`);
    };

    this.ws.onmessage = (ev: MessageEvent) => {
      this.store.dispatch(ACTION.WS_ON_MESSAGE, ev.data);
    };
  }

  submit(store: ActionContext<State, State>, sequence: number, sql: string, callback: () => void) {
    this.store = store;

    const requeset = new Req();
    requeset.sequence = sequence;
    requeset.sql = sql;
    const json = JSON.stringify(requeset);
    this.ws.send(json);
    callback();
  }
}
