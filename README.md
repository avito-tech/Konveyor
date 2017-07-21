## Overview

![License](https://img.shields.io/badge/license-MIT-blue.svg)

**Konveyor** is a tiny library which intends to battle `RecyclerView.Adapter` Hell, by introducing "Composition over inheritance"
 approach when dealing with RecyclerView adapters. While making your Adapter related logic testable by
 providing abstraction layer over its logic, it'll make you forget all of the ViewType hassle all together. 

# Contents

* [Installation](#installation)
    * [Gradle](#installation-gradle)
    * [Maven](#installation-maven)
* [Structure](#structure)
  * [Item](#item)
  * [ItemView](#itemview)
     * [Methods Overview](#itemview-methods)
  * [ItemPresenter](#item-presenter)
  * [ItemBlueprint](#item-blueprint)
    * [Methods Overview](#item-blueprint-methods)
  * [AdapterPresenter](#adapter-presenter)
* [Example](#example)

# <a name="installation" />Installation

## <a name="installation-gradle" />Gradle
**Step 1.** Add this in your root build.gradle

```groovy
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency
```groovy
	dependencies {
		compile 'com.avito.konveyor:VERSION'
	}
```

If you like to stay on the bleeding edge, or use certain commit for you dependency,
you can use the short commit hash or `anyBranch-SNAPSHOT` as the version

## <a name="installation-maven" />Maven
```xml
<dependency>
  <groupId>com.avito.konveyor</groupId>
  <artifactId>konveyor</artifactId>
  <version>VERSION</version>
</dependency>
```

# <a name="structure" />Structure
**Konveyor** introduces set of key abstractions to extract knowledge about ids and view types away
from `RecyclerView.Adapter`, thus, making all of the associated logic easily extensible and testable.

## <a name="item" />Item
_Item_  is an interface that represents data associated with a certain `ViewType`:
```kotlin
class Dog(override val id: Long) : Item
```

_id_ — identifier, that `RecyclerView.Adapter` will use in its methods.

## <a name="itemview" />ItemView
_ItemView_  is an abstraction over `RecyclerView.ViewHolder` 

```kotlin
interface DogView : ItemView {
    fun setName(name: String)
}

class DogViewHolder(view: View): BaseViewHolder(view), DogView {
    private val name = view.findViewById(R.id.name) as TextView

    override fun setName(name: String) {
        this.name.text = name
    }
}
```

## <a name="itemview-methods" />Methods Overview
`onUnbind()` is called whenever `RecyclerView.Adapter` calls `onViewRecycled()` method for this ItemView.
 You can override this method in case you need, for example, unsubscribe from listeners or release some resources associated with this `ViewHolder`.  


## <a name="item-presenter" />ItemPresenter
_ItemPresenter_  connects `Item` and its view. Essentially, your implementation of the interface should contain everything you would normally do in `onBindViewHolder()` method of `RecyclerView.Adapter`

```kotlin
class DogPresenter: ItemPresenter<DogItemView, Dog> {

    override fun bindView(view: DogView, item: Dog, position: Int) {
        view.setName(item.name)
    }

}
```

## <a name="item-blueprint" />ItemBlueprint
_ItemBlueprint_ is a key entity that ties together Android world with our abstractions

```kotlin
class DogBlueprint(override val presenter: ItemPresenter<DogView, Dog>)
    : ItemBlueprint<DogView, Dog> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
                layoutId = R.layout.dog_item,
                creator = { _, view -> DogViewHolder(view) }
        )

    override fun isRelevantItem(item: Item): Boolean = item is Dog

}
```

### <a name="item-blueprint-methods" />Methods Overview

* _presenter_ — presenter associated with this view and data.
* _viewHolderProvider_ — class to create associated `RecyclerView.ViewHolder`
* _isRelevantItem(item: Item)_ — you have to override this method to build connection between given `Item` and its `ItemBlueprint`.
 **Please mind, in the list of registered `ItemBlueprint`s every check has to be unique**.
 If multiple registered `ItemBlueprint`s respond `true` to a given `Item`, `ViewTypeCollisionException` will be thrown. 


## <a name="item-blueprint" />ItemBlueprint
_AdapterPresenter_ is an abstraction over `RecyclerView.Adapter`, that we can easily use in our unit tests. 
Current library provides default implementation for this interface - `SimpleAdapterPresenter`, this will satisfy 99.9% of your needs, unless you need something exotic.


## <a name="example" />Example
```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById(R.id.recycler) as RecyclerView
        val binder = ItemBinder.Builder()
                        .registerItem(createDogBlueprint())
                        .registerItem(createCatBlueprint())

        val adapterPresenter = SimpleAdapterPresenter(binder, binder)
        recycler.adapter = SimpleRecyclerAdapter(adapterPresenter, binder)
    }
```

In most cases your `SimpleRecyclerAdapter`, provided by this library will satisfy your needs.

This way you don't have to think about providing unique `ViewType`s for your data, as `ItemBinder` will handle this for you.

# Author
Philip Uvarov (philuvarov@gmail.com)

# License
MIT
