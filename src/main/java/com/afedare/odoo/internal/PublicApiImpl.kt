package com.afedare.odoo.internal

import com.afedare.odoo.api.OdooPublicApi
import de.timroes.axmlrpc.XMLRPCClient
import com.afedare.odoo.data.ServerVersion
import com.afedare.odoo.ext.removeEndSlash
import java.net.URL

internal class PublicApiImpl(host: String) : OdooPublicApi {
    private val commonApi = "/xmlrpc/2/common"
    private val client: XMLRPCClient

    init {
        if (host.isBlank())
            throw IllegalArgumentException("Blank url")
        client = XMLRPCClient(URL(host.removeEndSlash() + commonApi))
    }



    /**
     * @return user id, null when username or password is wrong
     * @throws de.timroes.axmlrpc.XMLRPCServerException when db is not correct
     */
    override fun authenticate(
        database: String,
        username: String,
        password: String,
        userAgent: Map<String, Any>
    ): Int? {
        val result = client.call("authenticate", database, username, password, userAgent)
        if (result is Int) {
            return result
        } else if (result is Boolean && !result) {
            return null
        }
        println("authenticate result type is unexpected $result")
        return null
    }

    override fun version(): ServerVersion {
        val version = client.call("version")
        return ServerVersion.fromMap(version as Map<String, Any>)
    }

    companion object {
        private const val TAG: String = "LoginApi"
    }
}