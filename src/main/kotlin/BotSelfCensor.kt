package org.laolittle.plugin

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessagePreSendEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.error
import net.mamoe.mirai.utils.info
import org.laolittle.plugin.api.BaiduAIPAPI.censor
import org.laolittle.plugin.api.BaiduAIPAPI.getToken

object BotSelfCensor : KotlinPlugin(
    JvmPluginDescription(
        id = "org.laolittle.plugin.BotSelfCensor",
        name = "BotSelfCensor",
        version = "1.0",
    ) {
        author("LaoLittle")
    }
) {
    override fun onEnable() {
        Config.reload()
        Data.reload()
        if (Config.verifyType == Config.ApiType.Normal && (Config.client_id.isBlank() || Config.client_id.isBlank())) logger.error { "未设置ClientId或ClientSecret！" }
        if (Config.verifyType == Config.ApiType.Sample && Data.cookie.isBlank()) logger.error { "未设置Cookie！" }
        this.launch {
            if (Data.access_token.isEmpty()) {
                Data.access_token = getToken()
            }
        }
        logger.info { "Plugin loaded" }
        GlobalEventChannel.subscribeAlways<MessagePreSendEvent> {
            val result = message.content.censor().toString()
            runCatching {
                message = when (result) {
                    "2" -> PlainText(Config.illegalMessage.random())
                    else -> message
                }
            }.onFailure {
                logger.error { "请求失败！" }
            }
        }

        /*  GlobalEventChannel.subscribeMessages {
              startsWith("发"){
                  if (it.isEmpty()) return@startsWith
                  subject.sendMessage(it)
              }
          }

         */
    }
}