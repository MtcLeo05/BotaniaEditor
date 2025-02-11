package com.mtcleo05.botania_editor;

import com.mojang.logging.LogUtils;
import com.mtcleo05.botania_editor.config.ClientConfig;
import com.mtcleo05.botania_editor.config.server.*;
import com.mtcleo05.botania_editor.utils.SpreaderOverride;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BotaniaEditor.MODID)
public class BotaniaEditor {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "botania_editor";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public BotaniaEditor(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener((ModConfigEvent.Loading event) -> {
            try {
                onConfigLoading(event);
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            try {
                onConfigReloading(event);
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, FunctioningFloraConfig.SPEC, MODID+"/functioning_flora.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GeneratingFloraConfig.SPEC, MODID+"/generating_flora.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, LotusConfig.SPEC, MODID+"/lotus.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ManaPoolConfig.SPEC, MODID+"/mana_pool.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SpreaderConfig.SPEC, MODID+"/spreader.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MiscConfig.SPEC, MODID+"/misc.toml");

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, MODID+"-client.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConfigLoading(ModConfigEvent.Loading event) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if(SpreaderConfig.SPEC.isLoaded()){
            SpreaderOverride.setupSpreaders();
        }

    }

    @SubscribeEvent
    public void onConfigReloading(ModConfigEvent.Reloading event) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if(SpreaderConfig.SPEC.isLoaded()){
            SpreaderOverride.setupSpreaders();
        }
    }
}
