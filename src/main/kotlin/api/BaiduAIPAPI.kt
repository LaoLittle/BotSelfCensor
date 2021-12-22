package org.laolittle.plugin.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import org.laolittle.plugin.Config
import org.laolittle.plugin.Config.client_id
import org.laolittle.plugin.Config.client_secret
import org.laolittle.plugin.Data.access_token
import org.laolittle.plugin.utils.KtorHttpUtil.post
import java.net.URLEncoder

object BaiduAIPAPI {
    private const val authUrl = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials"
    private const val textCensorUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined"
    private const val imageCensorUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined"

    private suspend inline fun String.normalCensor(): JsonObject {
        val body = "access_token=$access_token&text=$this"
        return body.post(textCensorUrl, "application/x-www-form-urlencoded")
    }

    private suspend inline fun String.sampleCensor(): JsonObject {
        val body = buildJsonObject {
            put("strategyId", 1)
            put("text", this@sampleCensor)
        }
        return body.toString().post(textCensorUrl, "application/json", true)
    }

    suspend fun getToken(): String {
        var url = authUrl
        url += "&client_id=$client_id"
        url += "&client_secret=$client_secret"
        val token = "{}".post(url, "")["access_token"]
        if (token != null)
            return token.toString()
        else throw NoSuchElementException("获取失败！")
    }

    suspend fun Image.imageCensor(): JsonObject {
        val imageUrl = withContext(Dispatchers.IO) {
            URLEncoder.encode(queryUrl(), "UTF-8")
        }
        val body = "access_token=$access_token&imgUrl=$imageUrl"
        return body.post(imageCensorUrl, "application/x-www-form-urlencoded")

    }

    suspend fun String.textCensor() =
        when (Config.verifyType) {
            Config.ApiType.Normal -> {
                this.normalCensor()
            }
            Config.ApiType.Sample -> {
                this.sampleCensor()
            }
        }["conclusionType"] ?: throw NoSuchElementException("获取失败！")
}