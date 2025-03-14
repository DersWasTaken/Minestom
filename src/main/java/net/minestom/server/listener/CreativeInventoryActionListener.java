package net.minestom.server.listener;

import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.client.play.ClientCreativeInventoryActionPacket;
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

import java.util.Objects;

public final class CreativeInventoryActionListener {
    public static void listener(ClientCreativeInventoryActionPacket packet, Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) return;
        short slot = packet.slot();
        final ItemStack item = packet.item();
        if (slot == -1) {
            // Drop item
            player.dropItem(item);
            return;
        }
        // Bounds check
        // 0 is crafting result inventory slot, ignore attempts to place into it
        if (slot < 1 || slot > PlayerInventoryUtils.OFFHAND_SLOT) {
            return;
        }
        // Set item
        slot = (short) PlayerInventoryUtils.convertWindow0SlotToMinestomSlot(slot);
        PlayerInventory inventory = player.getInventory();
        if (Objects.equals(inventory.getItemStack(slot), item)) {
            // Item is already present, ignore
            return;
        }
        inventory.setItemStack(slot, item);
    }
}
