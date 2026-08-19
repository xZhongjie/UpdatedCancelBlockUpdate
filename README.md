# 取消方块更新 (UCBU)

> 本仓库为 [CancelBlockUpdate](https://github.com/Phoupraw/CancelBlockUpdate)（原作者 Phoupraw，[mcmod](https://www.mcmod.cn/class/5695.html)）的更新维护版，主要新增了对新版本 Minecraft（1.20.4 及以后）的支持与多版本自动发布流程。

**【警告】** 在世界生成时启用本模组的效果可能会产生大量浮空方块，如果想生成正常的世界，请提前修改游戏规则。

取消方块更新、方块计划刻、随机刻和流体计划刻，允许无条件放置方块。

用于建造特殊建筑、制作和测试特殊地图。

## 新增游戏规则

- `cancelblockupdate:off`：为`false`时，取消所有更新；为`true`即原版；默认为`false`。

- `cancelblockupdate:replace`：为`false`时，草、蕨等方块不能被直接替换；为`true`即原版；默认为`false`。

## 新增指令

- `/cancelblockupdate random <pos>`：触发`pos`处方块的随机刻。

- `/cancelblockupdate schedule <pos>`：触发`pos`处方块的计划刻。
  建议搭配调整方块状态的调试棒使用。



