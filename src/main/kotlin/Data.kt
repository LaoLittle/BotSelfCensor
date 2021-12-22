package org.laolittle.plugin

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object Data : AutoSavePluginData("Data") {
    var access_token: String by value()

    var access_date: Int by value()

    val cookie: String by value()
}