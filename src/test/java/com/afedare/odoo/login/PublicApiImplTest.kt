package com.afedare.odoo.login

import com.afedare.odoo.api.OdooPublicApi
import com.afedare.odoo.ext.loadProperties
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import java.util.*

/**
 * Pls be careful about running against live server as it can alter its state.
 * This test exist because of the lack of as strict specification from odoo.
 * https://www.odoo.com/documentation/11.0/webservices/odoo.html
 *
 */
@Ignore
class PublicApiImplTest {
    private lateinit var client: OdooPublicApi

    @Before
    fun setUp() {
        val url = properties["odoo.url"] as String
        client = OdooPublicApi.newInstance(url)
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
    fun version() {
        val version = client.version()
        assertNotNull(version)
        println(version)
    }

    @Test
    fun authenticate() {
        val username = "admin"
        val password = "admin"
        val userId = client.authenticate(username = username, password = password)
        assertNotNull(userId)
        println(userId)
    }

}