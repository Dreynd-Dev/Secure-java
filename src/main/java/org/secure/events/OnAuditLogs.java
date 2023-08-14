package org.secure.events;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildAuditLogEntryCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.secure.core.Bot;
import org.secure.modules.AntiNuke;

import java.util.HashSet;

public class OnAuditLogs extends ListenerAdapter {

    HashSet<ActionType> actionsToTrigger = new HashSet<>();

    public OnAuditLogs() {

        actionsToTrigger.add(ActionType.CHANNEL_CREATE);
        actionsToTrigger.add(ActionType.CHANNEL_UPDATE);
        actionsToTrigger.add(ActionType.CHANNEL_DELETE);
        actionsToTrigger.add(ActionType.ROLE_CREATE);
        actionsToTrigger.add(ActionType.ROLE_UPDATE);
        actionsToTrigger.add(ActionType.ROLE_DELETE);
        actionsToTrigger.add(ActionType.BAN);
        actionsToTrigger.add(ActionType.UNBAN);
        actionsToTrigger.add(ActionType.BOT_ADD);
        actionsToTrigger.add(ActionType.WEBHOOK_CREATE);
        actionsToTrigger.add(ActionType.WEBHOOK_UPDATE);
        actionsToTrigger.add(ActionType.WEBHOOK_REMOVE);

    }

    @Override
    public void onGuildAuditLogEntryCreate(GuildAuditLogEntryCreateEvent event) {

        if (actionsToTrigger.contains(event.getEntry().getType())) {

            AntiNuke antiNuke = Bot.getInstance().getModuleInstance(event.getGuild().getId(), AntiNuke.class);

            if (antiNuke.enabled) {

                Member member = event.getGuild().getMemberById(event.getEntry().getUserIdLong());

                if (member != null)

                    antiNuke.newElement(member);

            }
        }
    }
}
