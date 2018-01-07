package info.matsumana.tsujun.model

data class ResponseError(val sequence: Int,
                         val sql: String,
                         val message: String)
