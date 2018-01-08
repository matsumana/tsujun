package info.matsumana.tsujun.model.ksql

data class KsqlResponseQueries(val queries: KsqlResponseQueriesInner)

data class KsqlResponseQueriesInner(val statementText: String, val queries: Array<KsqlResponseQueriesInnerQueries>)

data class KsqlResponseQueriesInnerQueries(val id: KsqlResponseQueriesInnerQueriesId, val kafkaTopic: String, val queryString: String)

data class KsqlResponseQueriesInnerQueriesId(val id: String)
