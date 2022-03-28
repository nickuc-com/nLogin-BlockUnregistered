/*
 * This file is part of a NickUC project
 *
 * Copyright (c) NickUC <nickuc.com>
 * https://github.com/nickuc
 */

package com.nickuc.login.extra;

import com.nickuc.login.api.nLoginAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BlockUnregistered extends JavaPlugin implements Listener {

    private static final String DEF_MESSAGE = "&cVocê só pode entrar nesse servidor se estiver registrado.";
    private static String MESSAGE;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        List<String> kickMessage = getConfig().getStringList("kick-message");
        String message = kickMessage.isEmpty() ? DEF_MESSAGE : String.join("\n§r", kickMessage);
        MESSAGE = ChatColor.translateAlternateColorCodes('&', message);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            String name = e.getName();
            if (!nLoginAPI.getApi().isRegistered(name)) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, MESSAGE);
            }
        }
    }

}
