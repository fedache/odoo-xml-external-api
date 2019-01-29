package com.afedare.odoo.internal

import com.afedare.odoo.api.OdooApi
import de.timroes.axmlrpc.XMLRPCClient
import com.afedare.odoo.ext.removeEndSlash
import java.net.URL

internal class OdooApiImpl(host: String, private val database: String, private val userId: Int, private val password: String) : OdooApi {

    private val commonApi = "/xmlrpc/2/object"
    private val executeQueryMethodName = "execute_kw"
    private val client: XMLRPCClient

    init {
        if (host.isBlank())
            throw IllegalArgumentException("Blank url")
        client = XMLRPCClient(URL(host.removeEndSlash() + commonApi), XMLRPCClient.FLAGS_DEBUG)
    }


    override fun checkAccessRights(modelName: String): Boolean {
        val methodName = "check_access_rights"
        val result = client.call(
            executeQueryMethodName, database, userId, password, modelName, methodName,
            arrayOf("read"), mapOf("raise_exception" to false)
        )
        return result as Boolean
    }

    @SuppressWarnings("unchecked")
    override fun search(
        modelName: String,
        domainList: Array<Array<Any>>,
        filter: Map<String, Int>
    ): Array<Int> {
        val methodName = "search"
        val result = searchCall(modelName, methodName, domainList, filter)
        val resultArray: List<Int> = (result as Array<Any>).map { it as Int }
        return resultArray.toTypedArray()
    }

    override fun count(
        modelName: String, domainList: Array<Array<Any>>, filter: Map<String, Int>): Int {
        val methodName = "search_count"
        val result = searchCall(modelName, methodName, domainList, filter)
        return (result as Int)
    }


    override fun searchRead(
        modelName: String,
        domainList: Array<Array<Any>>,
        filter: Map<String, Int>,
        filterFields: Array<String>
    ): Array<Map<String, Any>> {
        val methodName = "search_read"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            arrayOf(domainList),
            mapOf("fields" to filterFields) + filter
        )

        val resultArray = result as Array<Any>
        val resultMapOfObj: List<Map<String, Any>> = resultArray.map { it as Map<String, Any> }
        return resultMapOfObj.toTypedArray()
    }

    /**
     * Takes a list of ids and optionally a list of fields to fetch.
     * even if the id field is not requested, it is always returned
     */
    @SuppressWarnings("unchecked")
    override fun read(
        modelName: String,
        ids: Array<Int>,
        filterFields: Array<String>
    ): Array<Map<String, Any>> {
        val methodName = "read"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            arrayOf(ids),
            mapOf("fields" to filterFields)
        )
        val resultArray = result as Array<Any>
        val resultMapOfObj: List<Map<String, Any>> = resultArray.map { it as Map<String, Any> }
        return resultMapOfObj.toTypedArray()
    }

    @SuppressWarnings("unchecked")
    override fun fieldsGet(modelName: String): Map<String, Map<String, String>> {
        val methodName = "fields_get"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            emptyArray<Any>(),
            mapOf("attributes" to arrayOf("string", "help", "type"))
        )
        return result as Map<String, Map<String, String>>
    }


    override fun create(modelName: String, data: Map<String, Any>): Int {
        val methodName = "create"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            listOf(data)
        )
        return result as Int
    }

    override fun updateBatch(modelName: String, idsToUpdate: List<Int>, data: Map<String, Any>): Boolean {
        val methodName = "write"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            arrayOf(idsToUpdate, data)
        )
        return result as Boolean
    }

    override fun update(modelName: String, idsToUpdate: Int, data: Map<String, Any>): Boolean {
        return updateBatch(modelName, listOf(idsToUpdate), data)
    }

    @SuppressWarnings("unchecked")
    override fun delete(modelName: String, id: Int): Boolean {
        return deleteBatch(modelName, listOf(id))
    }

    @SuppressWarnings("unchecked")
    override fun deleteBatch(modelName: String, ids: List<Int>): Boolean {
        val methodName = "unlink"
        val result = client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            listOf(ids)
        )
        return result as Boolean
    }

    private fun searchCall(
        modelName: String,
        methodName: String,
        domainList: Array<Array<Any>>,
        filter: Map<String, Int>
    ): Any {
        return client.call(
            executeQueryMethodName,
            database,
            userId,
            password,
            modelName,
            methodName,
            arrayOf(domainList),
            filter
        )
    }


}