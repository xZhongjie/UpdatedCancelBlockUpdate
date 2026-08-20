/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import net.minecraft.util.Identifier;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

public final class CBUIdentifiers {

    public static final Identifier
      CHANNEL = of("channel"),
      OFF = of("off"),
      REPLACE = of("replace"),
      STACK_SLABS = of("stack_slabs"),
      BOOL_RULE = of("bool_rule");

    public static Identifier of(String path) {
        return Identifier.of(CancelBlockUpdate.MOD_ID, path);
    }

    private CBUIdentifiers() {
    }

}
