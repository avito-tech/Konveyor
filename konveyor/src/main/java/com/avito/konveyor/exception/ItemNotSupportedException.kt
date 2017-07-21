package com.avito.konveyor.exception

import com.avito.konveyor.blueprint.Item
import java.lang.Exception

class ItemNotSupportedException(item: Item) : Exception("Item is not supported. Item: $item")
