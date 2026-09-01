# 背部物品配置（NeoForge 1.21.1）

编辑 `config/broken_blade-client.toml`。原有纯物品 ID 继续可用；1.21.1 使用 `/give` 命令的数据组件语法：

```toml
["Mowzie's Mobs: The Broken Blade"]
WroughtnautBackSword = 'minecraft:diamond_sword[minecraft:custom_model_data=123,minecraft:damage=10]'
```

数据组件会完整保留。`minecraft:air` 可隐藏展示物品。无效物品、错误组件或尾随内容会记录原因并回退为钻石剑，避免渲染崩溃。

物品在实际渲染时使用实体所在客户端世界的注册表解析，并缓存解析结果。因此可使用整合包注册的数据组件；配置热重载后也会自动更新。
