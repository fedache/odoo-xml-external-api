package com.afedare.odoo.ext

import java.io.InputStream
import java.lang.IllegalStateException
import java.util.*

fun loadProperties(clazz: Class<*>): Properties {
    val properties = Properties()
    val classLoader = clazz.classLoader
    val stream: InputStream? = classLoader.getResourceAsStream("test.properties")
    if (stream != null) {
        stream.use {
            properties.load(it);
        }
    } else throw IllegalStateException("test.properties")
    return properties
}