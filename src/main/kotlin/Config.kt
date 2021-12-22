package org.laolittle.plugin

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("Config") {

    enum class ApiType {
        Normal,
        Sample
    }

    val client_id: String by value()

    val client_secret: String by value()

    @ValueDescription(
        """
        验证类型
        "Normal"代表使用自己的设置
        "Sample"则使用官方的示例Api，需要Cookie 
        """
    )
    val verifyType: ApiType by value(ApiType.Normal)

    @ValueDescription("审核不通过机器人所发送的消息")
    val illegalMessage: Set<String> by value(setOf("有些话我不能说", "我好像有不能说的东西"))
}