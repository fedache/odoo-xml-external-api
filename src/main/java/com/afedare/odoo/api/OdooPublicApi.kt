package com.afedare.odoo.api

import com.afedare.odoo.data.ServerVersion
import com.afedare.odoo.internal.PublicApiImpl


interface OdooPublicApi {
    fun version(): ServerVersion
    fun authenticate(
        database: String = "",
        username: String,
        password: String,
        userAgent: Map<String, Any> = emptyMap()
    ): Int?

    companion object {
        fun newInstance(host: String): OdooPublicApi {
            return PublicApiImpl(host)
        }
    }

}