<template>
  <div>
    <div v-for="result in results">
      <result-text
        v-if="result.mode === 0"
        :sequence="result.sequence"
        :sql="result.sql"
        :mode="result.mode"
        :text="result.text">
      </result-text>
      <result-table
        v-if="result.mode !== 0"
        :sequence="result.sequence"
        :sql="result.sql"
        :mode="result.mode"
        :data="result.data">
      </result-table>
    </div>
  </div>
</template>

<style lang="scss">
</style>

<script lang="ts" src="./Result.ts"></script>
