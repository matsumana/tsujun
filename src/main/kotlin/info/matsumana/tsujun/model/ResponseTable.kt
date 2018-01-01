package info.matsumana.tsujun.model

import java.util.*

data class ResponseTable(val sequence: Int,
                         val sql: String,
                         val mode: Int,
                         val data: Array<Any>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResponseTable

        if (sequence != other.sequence) return false
        if (sql != other.sql) return false
        if (mode != other.mode) return false
        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sequence
        result = 31 * result + sql.hashCode()
        result = 31 * result + mode
        result = 31 * result + Arrays.hashCode(data)
        return result
    }
}
