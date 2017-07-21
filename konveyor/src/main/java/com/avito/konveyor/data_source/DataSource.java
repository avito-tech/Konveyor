package com.avito.konveyor.data_source;

/**
 * Read-only data access interface
 */
public interface DataSource<T> {

    boolean isEmpty();

    int getCount();

    T getItem(int position);
}
