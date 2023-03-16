# Javassist patch utils

Utility for working with Javassist and patching methods.

## Features
```java
public String hello() {
    return "Hello world";
}

public String hello(String world, boolean b) {
        return "Hello " + world;
}
```

Get method signature
```java
 String sign = PatchUtils.getJavaMethodSignature(ctMethod);
// Output: hello(java.lang.String)
```

Get method by return data type
```java
 CtMethod hello = PatchUtils.getMethodByReturn("hello", "java.lang.String", ctClass);
```

Get method by parameters
```java
 CtMethod hello = PatchUtils.getMethodByParams("hello", "java.lang.String", ctClass, 
        "java.lang.String", 
        "java.lang.Boolean");
```

Check if the signature matches
```java
boolean signed = PatchUtils.checkSignature(ctMethod, 
        "java.lang.String", 
        "java.lang.Boolean");
```

There are also several methods for inserting code and replacing methods.

## Open source
JPU is an open source project distributed under the Apache License 2.0 <br>

## Getting started

1. Download the latest build from releases
2. Read the FAQ and examples
3. Enjoy!

## Status

The project is in beta. Use at your own risk. <br>

