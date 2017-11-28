import { ActionContext, ActionTree } from 'vuex';
import { MUTATION } from './mutation-types';
import { ACTION } from './action-types';
import { Api } from '../api';
import { State } from './State';
import { ResponseBase } from './model/ResponseBase';

const api = new Api();

const actions = <ActionTree<State, any>> {
  [ACTION.INPUT_SQL](store: ActionContext<State, State>, sql: string) {
    store.commit(MUTATION.INPUT_SQL, sql);
  },
  [ACTION.SUBMIT](store: ActionContext<State, State>) {
    api.submit(store, store.state.sequence, store.state.sql, () => {
      const row: ResponseBase = {
        sequence: store.state.sequence,
        sql: store.state.sql,
        mode: -1,
      };

      store.commit(MUTATION.SUBMIT, row);
    });
  },
  [ACTION.WS_ON_MESSAGE](store: ActionContext<State, State>, json: string) {
    store.commit(MUTATION.WS_ON_MESSAGE, json);
  },
};

export default actions;
