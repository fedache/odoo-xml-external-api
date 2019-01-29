package com.afedare.odoo.ext

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test

import org.junit.Assert.*

class RemoveSlashKtTest {

    @Test
    fun testRemoveSlash() {
        val s = "f.com/".removeEndSlash()
        assertThat(s, equalTo("f.com"))

        assertThat("f.com".removeEndSlash(), equalTo("f.com"))
        assertThat("".removeEndSlash(), equalTo(""))
    }
}