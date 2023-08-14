package org.secure.modules.core;

import net.dv8tion.jda.api.entities.Member;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PPUserModule extends Module {

    private final Map<String, List<Map<String, Instant>>> data;

    protected PPUserModule(boolean enabled) {

        super(
                enabled
        );

        this.data = new ConcurrentHashMap<>();

    }

    public void newElement(Member member) {

        String memberID = member.getId();

        List<Map<String, Instant>> list = data.computeIfAbsent(memberID, k -> new ArrayList<>());

        list.removeIf(element -> Instant.now().isAfter(element.get("expirationDate")));

        Map<String, Instant> element = Map.of("expirationDate", Instant.now().plusSeconds(5));

        list.add(element);

        if (list.size() > 5) {

            data.remove(memberID);

            punishment(member);

        }

    }

    protected abstract void punishment(Member member);

}
