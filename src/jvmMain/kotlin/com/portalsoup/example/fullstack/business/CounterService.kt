package com.portalsoup.example.fullstack.business

import com.portalsoup.example.fullstack.db.entity.CounterTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object CounterService {

    fun getCount(name: String): Int? {
        val rawResult = transaction {
            kotlin.runCatching {
                CounterTable.select { CounterTable.name eq name }.single()[CounterTable.count]
            }
        }

        println("Raw result:\n$rawResult")

        return rawResult
            .takeIf { it.isSuccess }
            ?.getOrNull()
            ?: transaction { createCount(name)
        }
    }

    private fun createCount(newName: String): Int {
        return transaction {
            CounterTable.insert {
                it[name] = newName
                it[count] = 0
            }
        }[CounterTable.count]
    }

    fun incrementCounter(name: String): Int {
        return updateCounter(name) { i -> i + 1 }
    }

    fun decrementCounter(name: String): Int {
        return updateCounter(name) { i -> i - 1 }
    }

    private fun updateCounter(name: String, changeF: (prev: Int) -> Int): Int {
        val maybePrevCount = getCount(name)

        return maybePrevCount?.let { prevCount ->
                transaction {
                    prevCount.let { previousCount ->
                        CounterTable.update({ CounterTable.name eq name }) {
                            it[count] = changeF(previousCount)
                        }
                    }
                }
            } ?: createCount(name)
    }
}
