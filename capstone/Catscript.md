# Catscript Guide


## Introduction

Catscript is a small statically typed programing language that compiles into JVM bytecode.

It also has some very unique features that were implemented like type - inference and NO MORE SEMI COLONS.

The reason we did this Language was to test our abilities and make a language that was the best of the big languages like Python and Java and make it into a language of its own.

## Features

### For - Statement 

The for statement in catscript is built to mimic the java for statement

* for example a for statement would be set as follows 

```javascript
for (identifier in expression){
    "Statement"
    "Statement"
    "Logic"
}
```
###If - Statement

The if statement is also much like any other coding language, with the ability to have if else, or continuous if's
```javascript
if(expression){
  statement  
}else{
    statement
}
```

###Print Statement

The print statements in Catscript are very similar to Python print statements

```javascript
print("Hello_World")
```

###Variable Statements

The variable statements in Catscript are also a breeze to deal with and are written like so, with type being inferred, we just type check to make sure it is non-void!

```javascript
var x = 10

var x : int = 10
```

###Assignment Statements

Assignment statement are much like java

```javascript
x = 10

var = 'easy'
```
###Function Calls

Function calls are again alot like python and are easy to do

```javascript
foo()

func1(1,2,3,4)
```

###Function Declarations

Function Declarations in CatScript are easy with return type declarations

```javascript
function foo(a,b,c) : int {
    for(){
        if(){
        }else{
        }
    }
    return int
}
```


## Type System

Catscript was also written with a relatively small typesystem for ease of use.

Which include:

* int - a 32 bit integer
* string - a java-style string
* bool - a boolean value
* list - a list of values with the type 'x'
* null - the null type
* object - any type of value

