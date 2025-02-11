package com.mtcleo05.botania_editor.config.server;

import net.minecraftforge.common.ForgeConfigSpec;

public class MiscConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> MANA_TO_FE_RATE;
    public static final ForgeConfigSpec.ConfigValue<Integer> FE_CAPACITY;

    static {
        BUILDER.push("Misc Config");

        MANA_TO_FE_RATE =
            BUILDER.comment("Fluxfield Mana to fe conversion rate")
                .defineInRange("Fluxfield mana to fe", 10, 0, Integer.MAX_VALUE);

        FE_CAPACITY=
            BUILDER.comment("Max capacity of Fluxfield")
                .defineInRange("Fluxfield fe capacity", 12800, 0, Integer.MAX_VALUE);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
