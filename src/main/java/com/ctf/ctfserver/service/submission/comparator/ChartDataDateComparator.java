package com.ctf.ctfserver.service.submission.comparator;

import com.ctf.ctfserver.domain.models.service.ChartServiceModel;

import java.util.Comparator;

public class ChartDataDateComparator implements Comparator<ChartServiceModel> {

    @Override
    public int compare(ChartServiceModel o1, ChartServiceModel o2) {
        return o1.getName().compareTo(o2.getName());
    }

    public static ChartDataDateComparator getInstance() {
        return new ChartDataDateComparator();
    }

}
