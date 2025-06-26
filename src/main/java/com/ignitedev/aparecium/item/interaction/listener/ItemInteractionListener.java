package com.ignitedev.aparecium.item.interaction.listener;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.basic.ClickableItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractionListener implements Listener {

  @Autowired
  private static ItemBase itemBase;
  
  @EventHandler
  public void onInteract(PlayerInteractEvent event){
    Action action = event.getAction();
    Player player = event.getPlayer();
    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

    if(itemInMainHand.isEmpty()){
      return;
    }
    ClickableItem clickableItem = itemBase.findByItemStack(itemInMainHand, ClickableItem.class);
    
    if(clickableItem == null){
      return;
    }
    if(action.isLeftClick()){
      clickableItem.getOnLeftClick().accept(player);
    } else if(action.isRightClick()){
      clickableItem.getOnRightClick().accept(player);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event){
    ClickType click = event.getClick();
    Player player = (Player) event.getWhoClicked();
    ItemStack clickedItem = event.getCurrentItem();

    if(clickedItem == null){
      return;
    }
    ClickableItem clickableItem = itemBase.findByItemStack(clickedItem, ClickableItem.class);

    if(clickableItem == null){
      return;
    }
    if(click.isRightClick()){
      clickableItem.getOnRightClick().accept(player);
    } else if(click.isLeftClick()){
      clickableItem.getOnLeftClick().accept(player);
    }
  }
}
