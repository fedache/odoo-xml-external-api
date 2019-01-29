package com.afedare.odoo.ext

/**
 * a.com/ -> a.com
 */
fun String.removeEndSlash(): String {
    if (this.isNotEmpty()) {
        val lastChar = this[this.length - 1]
        if (lastChar == '/') {
            return this.substring(0 until (this.length - 1))
        }
    }
    return this
}