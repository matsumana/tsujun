import { MutationTree } from 'vuex';
import * as _ from 'lodash';
import { MUTATION } from './mutation-types';
import { State } from './State';
import { ResponseBase } from './model/ResponseBase';
import { ResponseText } from './model/ResponseText';
import { ResponseTable } from './model/ResponseTable';
import { ResponseTableRow } from './model/ResponseTableRow';

const mutations = <MutationTree<State>> {
  [MUTATION.INPUT_SQL](state: State, sql: string) {
    state.sql = sql;
  },
  [MUTATION.SUBMIT](state: State, response: ResponseBase) {
    state.results.unshift(response);
  },
  [MUTATION.SUBMITED](state: State) {
    state.sequence = state.sequence + 1;
  },
  [MUTATION.ON_RESPONSE](state: State, json: string) {
    const responseRows = json.split(/\n/);

    for (const responseRow of responseRows) {

      if (responseRow === '') {
        continue;
      }

      const response: ResponseBase = JSON.parse(responseRow);

      for (const row of state.results) {
        row.mode = response.mode;
        if (row.sequence === response.sequence) {
          // apply response data to screen
          if (row.mode === 0) {
            // text
            (row as ResponseText).text = (response as ResponseText).text;
          } else {
            // table
            const responseTable = row as ResponseTable;
            if (responseTable.data === undefined) {
              responseTable.data = [];
            }
            responseTable.data.push((response as ResponseTableRow).data);
          }
        }
      }
    }

    // FIXME Since update of ResponseTable is not detected by vue.js, deepcopy and forcibly reflect it
    state.results = _.cloneDeep(state.results);
  },
};

export default mutations;
