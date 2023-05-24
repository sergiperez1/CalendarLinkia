/*package com.example.proyectofinaldam.Calendario;

import com.google.android.material.datepicker.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EventDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private HashMap<CalendarDay, Integer> colors;

    public EventDecorator(List<Integer> eventColors, List<CalendarDay> eventDates) {
        dates = new HashSet<>(eventDates);
        colors = new HashMap<>();
        for (int i = 0; i < eventDates.size(); i++) {
            colors.put(eventDates.get(i), eventColors.get(i));
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        int day = view.getDay();
        int month = view.getMonth();
        int year = view.getYear();

        CalendarDay date = CalendarDay.from(year, month, day);

        if (colors.containsKey(date)) {
            int color = colors.get(date);
            view.addSpan(new DotSpan(10, color));
        }
    }
}*/

