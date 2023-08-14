package org.secure.modules;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.secure.modules.core.PPUserModule;

import java.time.Duration;

public class AntiSpam extends PPUserModule {

    public AntiSpam() {
        super(true);
    }

    @Override
    protected void punishment(Member member) {

        Duration duration = Duration.ofDays(3);

        AuditableRestAction<Void> ban = member.timeoutFor(duration);
        ban.queue();

    }
}
