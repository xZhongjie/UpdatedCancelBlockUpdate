# 取消方块更新 (UCBU)

> 本仓库为 [CancelBlockUpdate](https://github.com/Phoupraw/CancelBlockUpdate)（原作者 Phoupraw，[mcmod](https://www.mcmod.cn/class/5695.html)）的更新维护版，主要新增了对新版本 Minecraft（1.20.4 及以后）的支持与多版本自动发布流程。
**【警告】** 在世界生成时启用本模组的效果可能会产生大量浮空方块，如果想生成正常的世界，请提前修改游戏规则。

取消方块更新、方块计划刻、随机刻和流体计划刻，允许无条件放置方块。

用于建造特殊建筑、制作和测试特殊地图。

## 支持的 Minecraft 版本

每个 Minecraft 大版本一个分支。本模组 (UCBU) 的 **jar 文件名直接写明适用的 Minecraft 版本范围**，命名规则为 `UpdatedCancelBlockUpdate-<模组版本>-mc<适用MC版本范围>.jar`，按下面这张表选：

| jar 文件名 | 适用的 Minecraft 版本 | 分支 | 构建状态 |
|---|---|---|---|
| `UpdatedCancelBlockUpdate-1.1.2-mc1.19-1.19.2.jar` | 1.19 ~ 1.19.2 | `1.19.2` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.19.2)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.20.1-1.20.3.jar` | 1.20.1 ~ 1.20.3 | `1.20.1` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.20.1)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.20.4.jar` | 1.20.4 | `1.20.4` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.20.4)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.21.0-1.21.1.jar` | 1.21.0 ~ 1.21.1 | `1.21.1` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.21.1)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.21.2-1.21.8.jar` | 1.21.2 ~ 1.21.8 | `1.21.4` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.21.4)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.21.9-1.21.10.jar` | 1.21.9 ~ 1.21.10 | `1.21.10` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.21.10)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc1.21.11.jar` | 1.21.11 | `1.21.11` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=1.21.11)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc26.1.jar` | 26.1 | `26.1` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=26.1)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |
| `UpdatedCancelBlockUpdate-1.1.2-mc26.2.jar` | 26.2 | `26.2` | [![Build](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml/badge.svg?branch=26.2)](https://github.com/xZhongjie/UpdatedCancelBlockUpdate/actions/workflows/build.yml) |

## 新增游戏规则

- `cancelblockupdate:off`：为`false`时，取消所有更新；为`true`即原版；默认为`false`。

- `cancelblockupdate:replace`：为`false`时，草、蕨等方块不能被直接替换；为`true`即原版；默认为`false`。

## 新增指令

- `/cancelblockupdate random <pos>`：触发`pos`处方块的随机刻。

- `/cancelblockupdate schedule <pos>`：触发`pos`处方块的计划刻。
  建议搭配调整方块状态的调试棒使用。

## 开发 / 发布

- 本仓库使用 GitHub Actions 自动构建并发布：推送到任意版本分支会触发 `Build`；推送形如 `v*` 的 tag（例如 `v1.1.2-mc26.2`）会自动构建并把 jar 上传为 GitHub Release。
- 每个分支的 `gradle.properties` 声明各自的 Minecraft / Yarn / Fabric Loader / Fabric API 版本、`mod_version`、`mc_version_range`（jar 名里 `mc` 后缀的内容）与 `java_version`（编译目标版本）。CI 统一使用 JDK 21 运行 Gradle（Loom 1.17+ 要求），再通过 `options.release` 编译出对应 Java 版本的字节码（旧分支为 Java 17）。
- 本地构建：`./gradlew build`（产物在 `build/libs/`）；需要代理时请把代理配置写入 `~/.gradle/gradle.properties`，不要提交到仓库。


