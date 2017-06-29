# common-utils

## ```class``` MapBuilder
快速的构建Map<K,V>实例
Easy to build a Map<K,V> instance.

## Usage

### create the builder
```
MapBuilder<String, String> builder = 
  MapBuilder.begin("hello", "world");
```
use method ```begin``` to create a ```MapBuilder``` instance, and indicate the generic type of the Map<```K,V```>, the arguments of method ```begin``` will be the first Entry in the Map

### add more entries

```
builder.and("good", "bye")
  .and("fine", "thank u")
  .and("ni", "hao");
```

### get the builded map

```
Map<String, String> map = builder.build();
```

### what about types beside ```String```
type of ```MapBuilder<K,V>``` is decided by the arguments' type of method ```begin```

```
MapBuilder<String, Integer> builder = 
  MapBuilder.begin("hello", 100);

MapBuilder<String, Object> builder = 
  MapBuilder.begin("a-key", (Object) "haha"));
```

### why u say **Easy**

somewhere u need a map
```
MapBuilder.begin("example", (Object) "1")
  .and("num", 100)
  .and("disabled", false)
  .and("params", MapBuilder.begin("param1", (Object) "nothing")
                  .and("param2", new int[]{0, 1, 2})
                  .and("bool", true)
                  .and("counter", new Integer(5))
                  .build())
  .and("ending", true)
  .build();
```
u can get a ```Map``` like:
```
{
  "example": "1",
  "num": 100,
  "disabled": false,
  "params": {
    "param1": "nothing",
    "param2": [0, 1, 2],
    "bool": true,
    "counter": 5
  },
  "ending": true
}
``` 

try it!

# commons-beanutils

[![TEST](https://img.shields.io/travis/HQIT/commons-beanutils.svg "travis-ci")](http://travis-ci.org/HQIT/commons-beanutils)

## usage
see tests

* construct

use static method ```com.cloume.commons.beanutils.Updater.wrap(R)``` create new instance, *Updater.wrap(```theObjectYouWantToUpdate```)* return the Updater<T> instance

```
class UpdatableClass { ... }
Updater<UpdatableClass> updater = Updater.wrap(new UpdatableClass());
```

* keys mapping

method ```com.cloume.commons.beanutils.Updater.mapping(String, String)``` use to map a key name to another, for example a class has a field named ```someField```, when there is a key named ```anotherField```, can use the ```mapping``` set the value of ```anotherField``` to the ```someField``` of target class instance. and method ```mapping``` return the Updater<T> instance self, so can write multi-mapping in chain

```
Updater<UpdatableClass> updater = updater
	.mapping("anotherField", "someField")
	.mapping("helloField", "hiField"); 
```

* update

there are two overwrited methods ```com.cloume.commons.beanutils.Updater.update(Map<String, Object>)``` and ```com.cloume.commons.beanutils.Updater.update(Map<String, Object>, IConverter)```, use to update the target instance from a Map<String, Object>.

1. update without converter

example:
```
class UpdatableClass {
	String someField;
	int hiField;
}

Map<String, Object> from = new HashMap<String, Object>();
from.put("someField", "hello,world");
from.put("helloField", 100);	///see mapping
UpdatableClass updated = updater.update(from);
```

1. update with converter

converter ```com.cloume.commons.beanutils.IConverter``` have two methods ```com.cloume.commons.beanutils.IConverter.convert(String, Object)``` must be implemented, and ```com.cloume.commons.beanutils.IConverter.pair(String, Object)``` provide an easy way create new ```Entry<K,V>``` instance. 
when using converter you can both maping key and modify the value to fit the class field needs.

example:
```
ObjectToUpdate updated = Updater.wrap(new ObjectToUpdate()).mapping("longValue2", "longValue").update(from,
	/// java8 can use lambda
	new IConverter() {
		@SuppressWarnings("unchecked")
		@Override
		public Entry<String, Object> convert(String key, Object value) {
			switch (key) {
			case "intValue": {
				return pair(key, Integer.parseInt(String.valueOf(value)));
			}
			case "arrayValue": {
				return pair(key, StringUtils.split(String.valueOf(value), ','));
			}
			case "stringValue2": {
				return pair("stringValue", value);
			}
			case "classValue": {
				return pair(key, Updater.wrap(new InnerClass()).update((Map<String, Object>) value));
			}
			}
			return pair(key, value);		///pass through
		}
	});
```

* complex field

example:
```
class InnerClass { String innerField; }
class UpdatableClass {
	String someField;
	int hiField;
	InnerClass classField;
}

Map<String, Object> from = new HashMap<String, Object>();
from.put("classField.innerField", "hello,world");
UpdatableClass updated = updater.update(from);
```