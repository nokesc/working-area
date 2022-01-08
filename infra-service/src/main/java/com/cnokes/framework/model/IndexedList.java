package com.cnokes.framework.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cnokes.framework.web.exception.HttpBadRequestException;

import lombok.Getter;

public class IndexedList<T, F, C extends Comparable<C>> {

    private final Function<T, F> getField;
    private final Function<F, C> getComparable;

    @Getter
    protected class TargetAndField implements Comparable<TargetAndField> {
        private final T target;
        private final F field;

        public TargetAndField(T target) {
            this.target = target;
            this.field = getField.apply(target);
        }

        @Override
        public int compareTo(TargetAndField o) {
            return getComparable.apply(field).compareTo(getComparable.apply(o.field));
        }
    }

    private final List<TargetAndField> index = new ArrayList<>();

    public IndexedList(Function<T, F> getField, Function<F, C> getComparable, List<T> index) {
        this.getField = getField;
        this.getComparable = getComparable;
        if (index != null) {
            for (T target : index) {
                add(target);
            }
        }
    }

    public boolean validate(T item) {
        try {
            validate(new TargetAndField(item));
            return true;
        } catch (HttpBadRequestException re) {
            return false;
        }
    }

    public void add(T item) {
        TargetAndField toInsert = new TargetAndField(item);
        int insertLocation = validate(toInsert);
        index.add(insertLocation, toInsert);
    }

    private int validate(TargetAndField toInsert) {
        int searchResult = Collections.binarySearch(index, toInsert);

        int insertLocation;
        if (searchResult >= 0) {
            preInsert_validateExact(toInsert, searchResult, index.get(searchResult));
            insertLocation = searchResult;
        } else {
            insertLocation = (searchResult + 1) * -1;
        }

        if (insertLocation > 0) {
            TargetAndField previous = index.get(insertLocation - 1);
            preInsert_validatePrevious(toInsert, insertLocation, previous);
        }
        if (insertLocation < index.size()) {
            TargetAndField next = index.get(insertLocation);
            preInsert_validateNext(toInsert, insertLocation, next);
        }
        preInsert_ready(toInsert, insertLocation);
        return insertLocation;
    }

    protected void preInsert_validateExact(TargetAndField toInsert, int insertLocation, TargetAndField existing) {
    }

    protected void preInsert_validatePrevious(TargetAndField toInsert, int insertLocation, TargetAndField previous) {
    }

    protected void preInsert_validateNext(TargetAndField toInsert, int insertLocation, TargetAndField next) {
    }

    protected void preInsert_ready(TargetAndField toInsert, int insertLocation) {
    }

    public List<T> getItems() {
        return index.stream().map(item -> item.target).collect(Collectors.toList());
    }
}
