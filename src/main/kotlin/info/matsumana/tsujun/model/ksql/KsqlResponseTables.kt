package info.matsumana.tsujun.model.ksql

data class KsqlResponseTables(val tables: KsqlResponseTablesInner)

data class KsqlResponseTablesInner(val statementText: String, val tables: Array<KsqlResponseTablesInnerTables>)

data class KsqlResponseTablesInnerTables(val name: String, val topic: String, val format: String, val isWindowed: Boolean)
