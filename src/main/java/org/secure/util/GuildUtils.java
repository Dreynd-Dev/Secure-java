package org.secure.util;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class GuildUtils {

    public static CompletableFuture<Member> getAuditLogActionMember(Guild guild, ActionType actionType) {

        CompletableFuture<Member> future = new CompletableFuture<>();

        guild.retrieveAuditLogs().queue(auditLogEntries -> {

            for (AuditLogEntry entry : auditLogEntries) {

                    if (entry.getType() == actionType) {

                        Member member = guild.getMember(Objects.requireNonNull(entry.getUser()));
                        future.complete(member);
                        return;

                    }
                }

                future.complete(null);

            });

            return future;
        }

}
