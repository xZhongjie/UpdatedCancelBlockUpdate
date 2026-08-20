/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
final class Chinese extends FabricLanguageProvider {

    Chinese(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder b) {
        String modName = "取消方块更新";
        b.add("modmenu.nameTranslation." + CancelBlockUpdate.MOD_ID, modName);
        b.add("modmenu.descriptionTranslation." + CancelBlockUpdate.MOD_ID, """
          §4§l【警告】§r在世界生成时启用本模组的效果可能会产生大量浮空方块，如果想生成正常的世界，请提前修改游戏规则。
          本模组是 CancelBlockUpdate（原作者 Phoupraw）的更新维护版，由 xZhongjie 维护，支持新版本 Minecraft。
          取消方块更新、方块计划刻、随机刻和流体计划刻，允许无条件放置方块。
          用于建造特殊建筑、制作和测试特殊地图。
          §l新增游戏规则：§r
          - §ocancelblockupdate:off§r：为§ofalse§r时，取消所有更新；为§otrue§r即原版；默认为§ofalse§r。
          - §ocancelblockupdate:replace§r：为§ofalse§r时，草、蕨等方块不能被直接替换；为§otrue§r即原版；默认为§ofalse§r。
          - §ocancelblockupdate:stack_slabs§r：为§otrue§r时，允许半砖堆叠成双层半砖；默认为§otrue§r。
          §l新增指令：§r
          - §o/cancelblockupdate random <pos>§r：触发§opos§r处方块的随机刻。
          - §o/cancelblockupdate schedule <pos>§r：触发§opos§r处方块的计划刻。
          建议搭配调整方块状态的调试棒使用。
          """);
        b.add(CBUGameRules.OFF.getTranslationKey(), modName + "：禁用模组全部效果");
        b.add(CBUGameRules.REPLACE.getTranslationKey(), modName + "：允许放置方块替换草、蕨");
        b.add(CBUGameRules.STACK_SLABS.getTranslationKey(), modName + "：允许半砖堆叠成双层半砖");
    }

}