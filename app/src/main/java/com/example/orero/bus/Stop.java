package com.example.orero.bus;

import com.example.orero.bus.schedule.Day;
import com.example.orero.bus.schedule.day.Slot;

import java.util.List;
import java.util.Map;

public class Stop {
    double lat;
    double lon;
    Map<String, List<Slot>> schedule;
}
