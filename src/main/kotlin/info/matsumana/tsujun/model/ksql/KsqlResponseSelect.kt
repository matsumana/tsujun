package info.matsumana.tsujun.model.ksql

import java.util.*

data class KsqlResponseSelect(val row: KsqlResponseSelectColumns)

data class KsqlResponseSelectColumns(val columns: Array<Any>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KsqlResponseSelectColumns

        if (!Arrays.equals(columns, other.columns)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(columns)
    }
}
