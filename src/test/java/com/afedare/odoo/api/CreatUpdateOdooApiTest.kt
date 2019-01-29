package com.afedare.odoo.api

import com.afedare.odoo.ext.loadProperties
import com.afedare.odoo.internal.OdooApiImpl
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.*

class CreatUpdateOdooApiTest {
    private lateinit var odooApiImpl: OdooApi

    @Before
    fun setUp() {
        val url = properties["odoo.url"] as String
        val database = properties["odoo.db_name"] as String
        val userId = (properties["odoo.user_id"] as String).toInt()
        val password = properties["odoo.password"] as String
        odooApiImpl = OdooApi.newInstance(url, database, userId, password)
    }

    companion object {
        private lateinit var properties: Properties

        @JvmStatic
        @BeforeClass
        fun setBeforeClass() {
            properties = loadProperties(this::class.java)
        }
    }

    @Test
    fun testCreate() {
        val data = mapOf("name" to "Tean", "device_id" to 200192)
        val result = odooApiImpl.create("unmc.preferences", data)
        println(result)
    }

    @Test
    fun testCreateWithExistingForeignKey() {
        val modelName = "unmc.call_records"
        val data0 = mapOf(
            "patient" to 1,
            "contact_type" to 1,
            "caller" to 1,
            "date_of_call" to "2019-01-27 14:20:25",
            "comment" to "Callllerr"
        )

        val data1 = mapOf(
            "patient" to 1,
            "contact_type" to 1,
            "caller" to 1,
            "date_of_call" to "2019-01-28 14:20:25",
            "comment" to "Caller was quick"
        )

        val result0 = odooApiImpl.create(modelName, data0)
        println(result0)

        val result1 = odooApiImpl.create(modelName, data1)
        println(result1)
    }


    @Test
    fun testUpdate() {
        val modelName = "unmc.patient"
        val data = mapOf("surname" to "Tanya")
        val result = odooApiImpl.updateBatch(modelName, listOf(1, 12), data)
        println(result)
    }

    @Test
    fun testDelete() {
        val modelName = "unmc.patient"
        val result = odooApiImpl.delete(modelName, 3)
        println(result)
    }

}