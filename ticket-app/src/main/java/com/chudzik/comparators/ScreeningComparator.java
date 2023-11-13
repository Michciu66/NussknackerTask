package com.chudzik.comparators;

import java.util.Comparator;

import com.chudzik.dtos.ScreeningDto;

public class ScreeningComparator implements Comparator<ScreeningDto> {

    @Override
    public int compare(ScreeningDto firstScreening, ScreeningDto secondScreening) {
        int nameCompare = firstScreening.getMovie().getTitle().compareTo(secondScreening.getMovie().getTitle());
        if (nameCompare != 0) {
            return nameCompare;
        }
        return firstScreening.getTime().compareTo(secondScreening.getTime());
    }

}
