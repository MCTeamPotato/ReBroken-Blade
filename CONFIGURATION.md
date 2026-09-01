# 背部物品配置（Forge 1.20.1）

编辑 `config/broken_blade-client.toml`，原有纯物品 ID 配置继续可用；也支持 `/give` 命令的物品 NBT 语法：

```toml
["Mowzie's Mobs: The Broken Blade"]
WroughtnautBackSword = 'minecraft:diamond_sword{CustomModelData:123,Damage:10,Enchantments:[{id:"minecraft:unbreaking",lvl:3s}]}'
```

物品 ID 后的复合标签会完整保留，包括附魔、自定义模型数据、名称、材质包或其他模组使用的 NBT。`minecraft:air` 可隐藏展示物品。无效物品或错误 NBT 会记录原因并回退为钻石剑，避免渲染初始化崩溃。

修改后重新进入游戏或按 F3+T 重建资源与渲染器，使新配置用于钢铁守护者的背部物品。
