package com.avito.konveyor.data_source

interface IterableDataSource<T> : DataSource<T>, Iterable<T>