import Vue from 'vue';
import Component from 'vue-class-component';
import Editor from './components/Editor.vue';
import Result from './components/Result.vue';

@Component({
  components: {
    Editor,
    Result,
  },
})
export default class App extends Vue {
}
