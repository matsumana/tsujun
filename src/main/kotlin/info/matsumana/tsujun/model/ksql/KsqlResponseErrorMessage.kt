package info.matsumana.tsujun.model.ksql

data class KsqlResponseErrorMessage(val message: String,
                                    val stackTrace: List<String>)
