package info.matsumana.tsujun.exception

class KsqlException(val sequence: Int,
                    val sql: String,
                    val statusCode: Int,
                    override val message: String) : RuntimeException()
