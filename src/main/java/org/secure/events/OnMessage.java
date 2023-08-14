package org.secure.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.secure.core.Bot;
import org.secure.modules.AntiSpam;

public class OnMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        AntiSpam antiSpam = Bot.getInstance().getModuleInstance(event.getGuild().getId(), AntiSpam.class);

        antiSpam.newElement(event.getMember());

    }
}
