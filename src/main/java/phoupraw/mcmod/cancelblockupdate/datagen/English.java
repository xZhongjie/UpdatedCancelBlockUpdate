package phoupraw.mcmod.cancelblockupdate.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
final class English extends FabricLanguageProvider {

    English(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder b) {
        String modName = "UpdatedCancelBlockUpdate (UCBU)";
        b.add("modmenu.nameTranslation." + CancelBlockUpdate.MOD_ID, modName);
        b.add("modmenu.descriptionTranslation." + CancelBlockUpdate.MOD_ID, """
          ?4?l[Warning]?r During world generation enable this mod might produce lots of floating blocks, if want to generate normal world, please change game rule in advance.
          Cancel block update, block schedule tick, random tick and fluid schedule tick, allow unlimited place block.
          For building special buildings, make and test special map.
          ?lNew game rules:?r
          - ?ocancelblockupdate:off?r: when ?ofalse?r, cancel all block update; when ?otrue?r it is vanilla; default ?ofalse?r.
          - ?ocancelblockupdate:replace?r: when ?ofalse?r, grass, fern and so on block can't be replaced directly; when ?otrue?r it is vanilla; default ?ofalse?r.
          ?lNew commands:?r
          - ?o/cancelblockupdate random <pos>?r: trigger random tick of block at ?opos?r.
          - ?o/cancelblockupdate schedule <pos>?r: trigger schedule tick of block at ?opos?r.
          Suggest with adapt block state debug stick to use.
          """);
        b.add(CBUGameRules.OFF.getDescriptionId(), modName + ": disable all effects of the mod");
        b.add(CBUGameRules.REPLACE.getDescriptionId(), modName + ": allow replace grass and fern");
    }

}

