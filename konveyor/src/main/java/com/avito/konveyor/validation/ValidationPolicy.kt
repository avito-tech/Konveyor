package com.avito.konveyor.validation

import com.avito.konveyor.ItemBinder
import com.avito.konveyor.konveyorConfiguration

/**
 * If set to `true` it will make [ItemBinder] throw in case of collisions or unregistered data
 * types, which is useful for debug builds to avoid unwanted behaviour in production.
 * Otherwise, [ItemBinder] will silently use stub views and ignore collisions.
 *
 * @see konveyorConfiguration
 */
interface ValidationPolicy {

    var validateEagerly: Boolean

}