package com.ctf.ctfserver.service.submission.collector;

import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.models.service.ChartServiceModel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
public class ChartServiceModelCollector implements Collector<ChartServiceModel, List<ChartServiceModel>, List<ChartServiceModel>> {


    public static ChartServiceModelCollector toChartServiceModel() {
        return new ChartServiceModelCollector();
    }

    @Override
    public Supplier<List<ChartServiceModel>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<ChartServiceModel>, ChartServiceModel> accumulator() {
        return (list, chartServiceModel) -> {
            setValue(list, chartServiceModel);
            list.add(chartServiceModel);
        };
    }

    @Override
    public BinaryOperator<List<ChartServiceModel>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<ChartServiceModel>, List<ChartServiceModel>> finisher() {
        return (list1) -> list1;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return new HashSet<>();
    }
    private void setValue(List<ChartServiceModel> list, ChartServiceModel chartServiceModel) {
        if(list.isEmpty()) {
            chartServiceModel.setValue(chartServiceModel.getValue());
        } else {
            chartServiceModel.setValue(chartServiceModel.getValue() + list.get(list.size() - 1).getValue());
        }
    }
}
