<template>
  <div>
    <div class="tile is-parent">
      <article class="tile is-child box">
        <div>SQL</div>
        <pre>{{ sql }}</pre>
        <br>
        <div>Result</div>
        <table class="table is-bordered is-striped is-narrow">
          <tbody>
          <tr v-for="(row, index) in data">
            <td>
              <b>{{ index + 1 }}</b>
            </td>
            <td v-for="(col, index) in row">
              {{ col }}
            </td>
          </tr>
          </tbody>
        </table>
      </article>
    </div>
  </div>
</template>

<style lang="scss">
</style>

<script lang="ts" src="./ResultTable.ts"></script>
