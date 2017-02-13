##0.8.0
###API Changes
Removed type parameter from `Scoped` interface.

##0.7.2
Add methods to create new `Context` instances from scope names as well as extract the scope name from a given `Context`.

##0.7.1
Fixed child scope disposal when parent is destroyed. 

It is highly recommended you run this version over `0.7.0` since old child scopes may be returned when they are expected to be destroyed.

##0.7.0
###API Changes
To facilitate the automatic cleanup of child scopes, a new component initialization pattern was added.

`Scoper#withParent` takes a `Context` or `String` (parent scope tag) and returns an instance of `ScopeBuilder`

Calling `createChild(Scoped, ChildBuilder)` will create your child component and associate it with the parent scope that was passed into `Scoper#withParent`

This will allow all child scopes to be automatically destroyed when `Scoper#destroyScope` is called on the parent scope.

There are no breaking API changes introduced in this version, however there will be a lint warning when `Scoper#getComponent` is used when building child scopes.

## 0.6.0
###Breaking API change added in this version
`Scoper#createComponent` now takes a `Scoped` instance rather than a `Context`
