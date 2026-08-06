package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class PresenceYearsCalculator {

    @Value("${nfctag.timezone}") private String timezone;

    public List<Integer> compute(OffsetDateTime earliest, OffsetDateTime latest){
        if (earliest == null || latest == null) { return List.of(); }

        ZoneId zone = ZoneId.of(this.timezone);

        return IntStream.rangeClosed(
                        earliest.atZoneSameInstant(zone).getYear(),
                        latest.atZoneSameInstant(zone).getYear())
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toList();
    }
}