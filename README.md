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