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
    {
      const response: ResponseBase = {
        sequence: store.state.sequence,
        sql: store.state.sql,
        mode: -1,
      };
      store.commit(MUTATION.SUBMIT, response);
    }

    api.submit(store.state.sequence, store.state.sql, (data: string) => {
      store.commit(MUTATION.ON_RESPONSE, data);
    });

    store.commit(MUTATION.SUBMITED);
  },
};

export default actions;
