package com.afedare.odoo.api

import com.afedare.odoo.internal.OdooApiImpl

interface OdooApi {
    fun checkAccessRights(modelName: String): Boolean
    fun search(modelName: String, domainList: Array<Array<Any>> = arrayOf(), filter: Map<String, Int> = emptyMap()): Array<Int>
    fun count(modelName: String, domainList: Array<Array<Any>> = arrayOf(), filter: Map<String, Int> = emptyMap()): Int
    fun searchRead(modelName: String, domainList: Array<Array<Any>> = arrayOf(), filter: Map<String, Int> = emptyMap(), filterFields: Array<String> = emptyArray()): Array<Map<String, Any>>
    fun read(modelName: String, ids: Array<Int> = arrayOf(), filterFields: Array<String> = arrayOf()): Array<Map<String, Any>>
    fun fieldsGet(modelName: String): Map<String, Map<String, String>>
    fun create(modelName: String, data: Map<String, Any>): Int
    fun updateBatch(modelName: String, idsToUpdate: List<Int>, data: Map<String, Any>): Boolean
    fun update(modelName: String, idsToUpdate: Int, data: Map<String, Any>): Boolean
    fun delete(modelName: String, id: Int): Boolean
    fun deleteBatch(modelName: String, ids: List<Int>): Boolean

    companion object {
        fun newInstance(host: String, database: String, userId: Int, password: String): OdooApi {
            return OdooApiImpl(host, database, userId, password)
        }
    }
}
