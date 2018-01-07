package info.matsumana.tsujun.model.ksql

data class KsqlResponseQueries(val queries: KsqlResponseQueriesInner)

data class KsqlResponseQueriesInner(val statementText: String, val queries: Array<KsqlResponseQueriesInnerQueries>)

data class KsqlResponseQueriesInnerQueries(val id: Int, val kafkaTopic: String, val queryString: String)
