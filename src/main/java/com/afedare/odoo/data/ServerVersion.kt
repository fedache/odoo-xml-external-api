package com.afedare.odoo.data

/**
 * @param serverVersion major version
 * @param serverVersionInfo Full version
 *
 */
data class ServerVersion(
        val serverVersion: String,
        val serverVersionInfo: String,
        val serverSerie: String,
        val protocolVersion: String
) {
    companion object {
        @SuppressWarnings("unchecked")
        fun fromMap(map: Map<String, Any>): ServerVersion {
            val serverVersionInfo = map["server_version_info"]
            val serverVersionInfoString = if (serverVersionInfo is Array<*>)
                serverVersionInfo.joinToString(separator = "-", transform = { it.toString() })
            else ""
            return ServerVersion(
                    map["server_version"].toString(), serverVersionInfoString,
                    map["server_serie"].toString(), map["protocol_version"].toString()
            )
        }
    }
}