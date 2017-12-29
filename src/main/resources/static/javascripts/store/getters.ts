import { GetterTree } from 'vuex';
import { State } from './State';

const getters = <GetterTree<State, any>> {
  results: (state: State) => state.results,
};

export default getters;
