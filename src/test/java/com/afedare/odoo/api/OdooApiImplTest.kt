package com.afedare.odoo.api

import com.afedare.odoo.ext.loadProperties
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import java.util.*


@Ignore
class OdooApiImplTest {

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
    fun checkAccessRights() {
        val isAllowed = odooApiImpl.checkAccessRights("res.partner")
        assertThat(isAllowed, equalTo(true))
    }

    @Test
    fun noDomainSearch() {
        val listOfIds = odooApiImpl.search("res.partner")
        println(listOfIds.joinToString())
    }

    @Test
    fun domainSearch() {
        val listOfIds: Array<Int> = odooApiImpl.search("res.partner", arrayOf(arrayOf("id", "=", 1)))
        println(listOfIds.joinToString())
        assertThat(listOfIds.size, equalTo(1))
    }

    @Test
    fun domainSearchFilter() {
        val listOfIds: Array<Int> = odooApiImpl.search(
                "res.partner", filter = mapOf("offset" to 0, "limit" to 2)
        )
        println(listOfIds.joinToString())
        assertThat(listOfIds.size, equalTo(2))
    }


    @Test
    fun noDataSearch() {
        val listOfIds: Array<Int> = odooApiImpl.search(
                "res.partner", domainList = arrayOf(arrayOf("id", "=", -12))
        )
        println(listOfIds.joinToString())
        assertThat(listOfIds.size, equalTo(0))
    }

    @Test
    fun count() {
        val count: Int = odooApiImpl.count("res.partner", domainList = arrayOf(arrayOf("id", "=", 1)))
        println(count)
        assertThat(count, equalTo(1))
    }

    @Test
    fun testCountNoData() {
        val count: Int = odooApiImpl.count("res.partner", domainList = arrayOf(arrayOf("id", "=", -20)))
        println(count)
        assertThat(count, equalTo(0))
    }


    @Test
    fun testRead() {
        val result = odooApiImpl.read("res.partner", arrayOf(1, 6))
        println(result)
        assertThat(result.size, equalTo(2))
    }

    @Test
    fun testSearchRead() {
        val result = odooApiImpl.searchRead("res.partner", filter = mapOf("offset" to 0, "limit" to 2))
        println(result.joinToString())
        assertThat(result.size, equalTo(2))
    }

    @Test
    fun testReadFilterFields() {
        val result = odooApiImpl.read("res.partner", arrayOf(1, 20), arrayOf("name", "country_id", "comment"))
        println(result.joinToString())
        for (map in result) {
            if (!(map.containsKey("name")
                            && map.containsKey("country_id")
                            && map.containsKey("comment")
                            && map.containsKey("id"))
            ) {
                fail()
            }
        }
    }

    @Test
    fun testFieldsGet() {
        val result = odooApiImpl.fieldsGet("res.partner")
        println(result)
    }


}