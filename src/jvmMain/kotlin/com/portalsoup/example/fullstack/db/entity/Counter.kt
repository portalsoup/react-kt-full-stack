package com.portalsoup.example.fullstack.db.entity

import com.portalsoup.example.fullstack.common.resources.CounterResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CounterTable : IntIdTable() {
    val name = varchar("name", 255)
    val count = integer("count")
}

class Counter(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Counter>(CounterTable)

    var name by CounterTable.name
    var count by CounterTable.count
}

fun Counter.toResource(): CounterResource = CounterResource(name, count)
