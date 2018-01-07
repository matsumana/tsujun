package info.matsumana.tsujun.model.ksql

data class KsqlResponseStreams(val streams: KsqlResponseStreamsInner)

data class KsqlResponseStreamsInner(val statementText: String, val streams: Array<KsqlResponseStreamsInnerStreams>)

data class KsqlResponseStreamsInnerStreams(val name: String, val topic: String, val format: String)
