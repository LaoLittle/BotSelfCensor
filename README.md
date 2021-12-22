## BotSelfCensor 机器人自我审核插件
[项目地址](https://github.com/LaoLittle/BotSelfCensor)
[Release](https://github.com/LaoLittle/BotSelfCensor/releases)
正如其名，机器人会审核自己即将发送的消息，并判断是否合规，不合规则发送自定义消息

-----
**使用说明**：将本插件放入plugins文件夹，启动一次mirai-console并关闭
然后在`config/org.laolittle.plugin.BotSelfCensor/Config.yml`内填入百度云审核平台的`client_id`和`client_secret`

[百度云审核平台](https://console.bce.baidu.com/ai/?_=1640101212732&fromai=1#/ai/antiporn/overview/index)

-----

**TODO**

* 支持图片

配置文件示例
```
client_id: ''
client_secret: ''
# 验证类型
# "Normal"代表使用自己的设置
# "Sample"则使用官方的示例Api，需要Cookie
verifyType: Normal
# 审核不通过机器人所发送的消息
illegalMessage: 
  - 有些话我不能说
  - 我好像有不能说的东西
```