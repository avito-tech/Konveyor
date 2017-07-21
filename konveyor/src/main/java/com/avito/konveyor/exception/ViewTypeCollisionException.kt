package com.avito.konveyor.exception

import com.avito.konveyor.blueprint.Item
import java.lang.Exception

class ViewTypeCollisionException(item: Item) : Exception("Item can be handled by multiple presenters in the given ItemBinder. Item: $item")