package name.ball.joshua.spigot.trace;

import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.*;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.lang.reflect.Modifier;
import java.util.*;

public class Events {

    private List<Class<? extends Event>> eventClasses;
    private List<Class<? extends Event>> abstractEventClasses;

    public Events() {
        List<Class<? extends Event>> events = new ArrayList<Class<? extends Event>>();
        events.add(BlockBreakEvent.class);
        events.add(BlockBurnEvent.class);
        events.add(BlockCanBuildEvent.class);
        events.add(BlockDamageEvent.class);
        events.add(BlockDispenseEvent.class);
        events.add(BlockExpEvent.class);
        events.add(BlockExplodeEvent.class);
        events.add(BlockFadeEvent.class);
        events.add(BlockFormEvent.class);
        events.add(BlockFromToEvent.class);
        events.add(BlockGrowEvent.class);
        events.add(BlockIgniteEvent.class);
        events.add(BlockMultiPlaceEvent.class);
        events.add(BlockPhysicsEvent.class);
        events.add(BlockPistonExtendEvent.class);
        events.add(BlockPistonRetractEvent.class);
        events.add(BlockPlaceEvent.class);
        events.add(BlockRedstoneEvent.class);
        events.add(BlockSpreadEvent.class);
        events.add(EntityBlockFormEvent.class);
        events.add(LeavesDecayEvent.class);
        events.add(NotePlayEvent.class);
        events.add(SignChangeEvent.class);

        events.add(EnchantItemEvent.class);
        events.add(PrepareItemEnchantEvent.class);

        events.add(CreatureSpawnEvent.class);
        events.add(CreeperPowerEvent.class);
        events.add(EntityBreakDoorEvent.class);
        events.add(EntityChangeBlockEvent.class);
        events.add(EntityCombustByBlockEvent.class);
        events.add(EntityCombustByEntityEvent.class);
        events.add(EntityCombustEvent.class);
        events.add(EntityCreatePortalEvent.class);
        events.add(EntityDamageByBlockEvent.class);
        events.add(EntityDamageByEntityEvent.class);
        events.add(EntityDamageEvent.class);
        events.add(EntityDeathEvent.class);
        events.add(EntityExplodeEvent.class);
        events.add(EntityInteractEvent.class);
        events.add(EntityPortalEnterEvent.class);
        events.add(EntityPortalEvent.class);
        events.add(EntityPortalExitEvent.class);
        events.add(EntityRegainHealthEvent.class);
        events.add(EntityShootBowEvent.class);
        events.add(EntitySpawnEvent.class);
        events.add(EntityTameEvent.class);
        events.add(EntityTargetEvent.class);
        events.add(EntityTargetLivingEntityEvent.class);
        events.add(EntityTeleportEvent.class);
        events.add(EntityUnleashEvent.class);
        events.add(ExpBottleEvent.class);
        events.add(ExplosionPrimeEvent.class);
        events.add(FoodLevelChangeEvent.class);
        events.add(HorseJumpEvent.class);
        events.add(ItemDespawnEvent.class);
        events.add(ItemSpawnEvent.class);
        events.add(PigZapEvent.class);
        events.add(PlayerDeathEvent.class);
        events.add(PlayerLeashEntityEvent.class);
        events.add(PotionSplashEvent.class);
        events.add(ProjectileHitEvent.class);
        events.add(ProjectileLaunchEvent.class);
        events.add(SheepDyeWoolEvent.class);
        events.add(SheepRegrowWoolEvent.class);
        events.add(SlimeSplitEvent.class);
        events.add(SpawnerSpawnEvent.class);

        events.add(HangingBreakByEntityEvent.class);
        events.add(HangingBreakEvent.class);
        events.add(HangingPlaceEvent.class);

        events.add(BrewEvent.class);
        events.add(CraftItemEvent.class);
        events.add(FurnaceBurnEvent.class);
        events.add(FurnaceExtractEvent.class);
        events.add(FurnaceSmeltEvent.class);
        events.add(InventoryClickEvent.class);
        events.add(InventoryCloseEvent.class);
        events.add(InventoryCreativeEvent.class);
        events.add(InventoryDragEvent.class);
        events.add(InventoryEvent.class);
        events.add(InventoryMoveItemEvent.class);
        events.add(InventoryOpenEvent.class);
        events.add(InventoryPickupItemEvent.class);
        events.add(PrepareItemCraftEvent.class);

        events.add(PaintingBreakByEntityEvent.class);
        events.add(PaintingBreakEvent.class);
        events.add(PaintingPlaceEvent.class);

        events.add(AsyncPlayerChatEvent.class);
        events.add(AsyncPlayerPreLoginEvent.class);
        events.add(PlayerAchievementAwardedEvent.class);
        events.add(PlayerAnimationEvent.class);
        events.add(PlayerArmorStandManipulateEvent.class);
        events.add(PlayerBedEnterEvent.class);
        events.add(PlayerBedLeaveEvent.class);
        events.add(PlayerBucketEmptyEvent.class);
        events.add(PlayerBucketFillEvent.class);
        events.add(PlayerChangedWorldEvent.class);
        events.add(PlayerChatEvent.class);
        events.add(PlayerChatTabCompleteEvent.class);
        events.add(PlayerCommandPreprocessEvent.class);
        events.add(PlayerDropItemEvent.class);
        events.add(PlayerEditBookEvent.class);
        events.add(PlayerEggThrowEvent.class);
        events.add(PlayerExpChangeEvent.class);
        events.add(PlayerFishEvent.class);
        events.add(PlayerGameModeChangeEvent.class);
        events.add(PlayerInteractAtEntityEvent.class);
        events.add(PlayerInteractEntityEvent.class);
        events.add(PlayerInteractEvent.class);
        events.add(PlayerInventoryEvent.class);
        events.add(PlayerItemBreakEvent.class);
        events.add(PlayerItemConsumeEvent.class);
        events.add(PlayerItemDamageEvent.class);
        events.add(PlayerItemHeldEvent.class);
        events.add(PlayerJoinEvent.class);
        events.add(PlayerKickEvent.class);
        events.add(PlayerLevelChangeEvent.class);
        events.add(PlayerLoginEvent.class);
        events.add(PlayerMoveEvent.class);
        events.add(PlayerPickupItemEvent.class);
        events.add(PlayerPortalEvent.class);
        events.add(PlayerPreLoginEvent.class);
        events.add(PlayerQuitEvent.class);
        events.add(PlayerRegisterChannelEvent.class);
        events.add(PlayerRespawnEvent.class);
        events.add(PlayerShearEntityEvent.class);
        events.add(PlayerStatisticIncrementEvent.class);
        events.add(PlayerTeleportEvent.class);
        events.add(PlayerToggleFlightEvent.class);
        events.add(PlayerToggleSneakEvent.class);
        events.add(PlayerToggleSprintEvent.class);
        events.add(PlayerUnleashEntityEvent.class);
        events.add(PlayerUnregisterChannelEvent.class);
        events.add(PlayerVelocityEvent.class);

        events.add(MapInitializeEvent.class);
        events.add(PluginDisableEvent.class);
        events.add(PluginEnableEvent.class);
        events.add(RemoteServerCommandEvent.class);
        events.add(ServerCommandEvent.class);
        events.add(ServerListPingEvent.class);
        events.add(ServiceRegisterEvent.class);
        events.add(ServiceUnregisterEvent.class);

        events.add(VehicleBlockCollisionEvent.class);
        events.add(VehicleCreateEvent.class);
        events.add(VehicleDamageEvent.class);
        events.add(VehicleDestroyEvent.class);
        events.add(VehicleEnterEvent.class);
        events.add(VehicleEntityCollisionEvent.class);
        events.add(VehicleExitEvent.class);
        events.add(VehicleMoveEvent.class);
        events.add(VehicleUpdateEvent.class);

        events.add(LightningStrikeEvent.class);
        events.add(ThunderChangeEvent.class);
        events.add(WeatherChangeEvent.class);

        events.add(ChunkLoadEvent.class);
        events.add(ChunkPopulateEvent.class);
        events.add(ChunkUnloadEvent.class);
        events.add(PortalCreateEvent.class);
        events.add(SpawnChangeEvent.class);
        events.add(StructureGrowEvent.class);
        events.add(WorldInitEvent.class);
        events.add(WorldLoadEvent.class);
        events.add(WorldSaveEvent.class);
        events.add(WorldUnloadEvent.class);

        events.add(EntityDismountEvent.class);
        events.add(EntityMountEvent.class);

        events.add(PlayerSpawnLocationEvent.class);

        eventClasses = Collections.unmodifiableList(events);

        Set<Class<? extends Event>> abstractClasses = new LinkedHashSet<Class<? extends Event>>();
        abstractClasses.add(Event.class);
        for (Class<? extends Event> eventClass : events) {
            Class<?> klass = eventClass;
            while ((klass = klass.getSuperclass()) != Event.class) {
                if (Modifier.isAbstract(klass.getModifiers())) {
                    abstractClasses.add((Class<? extends Event>)klass);
                }
            }
        }

        abstractEventClasses = Collections.unmodifiableList(new ArrayList<Class<? extends Event>>(abstractClasses));
    }

    public List<Class<? extends Event>> getEvents() {
        return eventClasses;
    }

    public List<Class<? extends Event>> getAbstractEventClasses() {
        return abstractEventClasses;
    }

    public List<Class<? extends Event>> getAllEventClasses() {
        List<Class<? extends Event>> events = getEvents();
        List<Class<? extends Event>> aec = getAbstractEventClasses();

        List<Class<? extends Event>> result = new ArrayList<Class<? extends Event>>(events.size() + aec.size());
        result.addAll(events);
        result.addAll(aec);
        return result;
    }
}
