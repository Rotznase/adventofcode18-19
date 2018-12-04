package de.streubel.aoc18;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import de.streubel.AdventOfCodeRunner;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day04 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        SortedMap<DateTime, LogEntry> log = scanInput(input);
        provideMissingGuardIds(log);

        List<LogEntry> logEntriesAfterMidnight = new ArrayList<>(log.values());


        Multimap<Integer, Range<Integer>> cycles = recordSleepCycles(logEntriesAfterMidnight);

        int guardCandidate = findGuardAsleepMost(cycles);

        int minuteCandidate = findMinuteMaxAsleep(cycles.get(guardCandidate))[0];

        System.out.println("Result Part 1: "+(guardCandidate*minuteCandidate));


        minuteCandidate = 0;
        guardCandidate = -1;
        int maxMinuteCount = 0;
        for (int guardId: cycles.keySet()) {
            int[] minutesAsleep = findMinuteMaxAsleep(cycles.get(guardId));
            if (minutesAsleep[1] > maxMinuteCount) {
                maxMinuteCount = minutesAsleep[1];
                minuteCandidate = minutesAsleep[0];
                guardCandidate = guardId;
            }
        }
        System.out.println("Result Part 2: "+(guardCandidate*minuteCandidate));
    }

    private int[] findMinuteMaxAsleep(Collection<Range<Integer>> sleepCycles) {
        int count=0;
        int maxCount = 0;
        int minuteCandidate = -1;
        for (int minute=0; minute<60; minute++) {
            for (Range<Integer> range: sleepCycles) {
                if (range.contains(minute)) {
                    count++;
                }
            }
            if (count > maxCount) {
                minuteCandidate = minute;
                maxCount = count;
            }
            count = 0;
        }
        return new int[] {minuteCandidate, maxCount};
    }

    private Multimap<Integer, Range<Integer>> recordSleepCycles(List<LogEntry> logEntriesAfterMidnight) {
        Multimap<Integer, Range<Integer>> x = ArrayListMultimap.create();
        DateTime start = null;
        DateTime end   = null;
        for (LogEntry logEntry: logEntriesAfterMidnight) {
            switch (logEntry.action) {
                case "begins shift":
                    start = null;
                    end   = null;
                    break;
                case "falls asleep":
                    start = logEntry.time;
                    break;
                case "wakes up":
                    end = logEntry.time;
                    break;
            }

            if (start != null && end != null) {
                x.put(logEntry.guardId, Range.closedOpen(start.getMinuteOfHour(), end.getMinuteOfHour()));
                start = null;
                end   = null;
            }
        }
        return x;
    }

    private int findGuardAsleepMost(Multimap<Integer, Range<Integer>> cycles) {
        int guardCandidate = -1;
        int maxMinutes = 0;
        for (Integer guardId: cycles.asMap().keySet()) {
            int minutes = 0;
            Collection<Range<Integer>> intervalsAsleep = cycles.get(guardId);
            for (Range<Integer> intervalAsleep: intervalsAsleep) {
                minutes += intervalAsleep.upperEndpoint() - intervalAsleep.lowerEndpoint();
            }

            if (minutes > maxMinutes) {
                guardCandidate = guardId;
                maxMinutes = minutes;
            }
        }
        return guardCandidate;
    }

    private void provideMissingGuardIds(SortedMap<DateTime, LogEntry> log) {
        int currentGuardId = 0;
        for (DateTime time: log.keySet()) {
            LogEntry logEntry = log.get(time);
            if (logEntry.guardId != null) {
                currentGuardId = logEntry.guardId;
            } else {
                logEntry.guardId = currentGuardId;
            }
        }
    }

    private SortedMap<DateTime, LogEntry> scanInput(List<String> input) {
        SortedMap<DateTime, LogEntry> log = new TreeMap<>();
        for (String s: input) {
            LogEntry logEntry = LogEntry.parse(s);
            log.put(logEntry.time, logEntry);
        }
        return log;
    }

    private static class LogEntry {
        Integer guardId;
        DateTime time;
        String action;

        LogEntry(Integer guardId, DateTime time, String action) {
            this.guardId = guardId;
            this.time = time;
            this.action = action;
        }

        public String toString() {
            if (action.contains("begins shift")) {
                return String.format("[%s] Guard #%02d %s", time.toString(formatter), guardId, action);
            } else {
                return String.format("[%s] %s", time.toString(formatter), action);
            }
        }

        private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        private static LogEntry parse(String s) {
            Pattern pattern = Pattern.compile("\\[(.*)] (Guard #(\\d+) )?(.*)");
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches())
                throw new RuntimeException();
            DateTime time = DateTime.parse(matcher.group(1), formatter);
            Integer guardId = matcher.group(3) != null ? Integer.valueOf(matcher.group(3)) : null;
            String action = matcher.group(4);
            return new LogEntry(guardId, time, action);
        }

    }
}
