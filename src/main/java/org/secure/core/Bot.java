package org.secure.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.secure.events.OnAuditLogs;
import org.secure.events.OnMessage;
import org.secure.events.OnReady;
import org.secure.files.Data;
import org.secure.modules.core.Module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bot {

    private static final Bot INSTANCE = new Bot();

    private final ShardManager shardManager;
    private final Data data;
    private final Map<String, Map<String, Module>> modulesInstances = new ConcurrentHashMap<>();

    public Bot() {

        shardManager = DefaultShardManagerBuilder.createDefault(token)
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new OnReady(), new OnMessage(), new OnAuditLogs())
                .build();

        data = new Data();

    }

    public <T extends Module> T getModuleInstance(String guildID, Class<T> moduleClass) {

        Map<String, Module> modulesMap = modulesInstances.computeIfAbsent(guildID, k -> new ConcurrentHashMap<>());

        return moduleClass.cast(modulesMap.computeIfAbsent(moduleClass.getName(), k -> {

            try {

                return moduleClass.getDeclaredConstructor().newInstance();

            } catch (Exception e) {

                return null;

            }
        }));
    }

    public static Bot getInstance() {

        return INSTANCE;

    }

    public ShardManager getShardManager() {

        return shardManager;

    }

    public Data getData() {

        return data;

    }

    public void run() {

        try {

            for (JDA shard : shardManager.getShards()) {

                shard.awaitReady();

            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }
    }
}
