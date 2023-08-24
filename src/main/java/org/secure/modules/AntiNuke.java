package org.secure.modules;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.secure.modules.core.PPUserModule;

import java.util.concurrent.TimeUnit;

public class AntiNuke extends PPUserModule {


    public AntiNuke() {
        super(true);
    }

    @Override
    protected void punishment(Member member) {

        AuditableRestAction<Void> ban = member.ban(0, TimeUnit.SECONDS);
        ban.queue();

    }
}
