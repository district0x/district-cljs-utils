# district-cljs-utils

[![Build Status](https://travis-ci.org/district0x/district-cljs-utils.svg?branch=master)](https://travis-ci.org/district0x/district-cljs-utils)


Set of helper functions for working with vanilla Clojurescript, requiring no other 3rd party libraries. This is not meant 
to be replacement of similar libraries like [medley](https://github.com/weavejester/medley), rather should be complementary.


## Installation
Add `[district0x/district-cljs-utils "1.0.4"]` into your project.clj  
Include `[district.cljs-utils]` in your CLJS file  

## API Overview
- [district.cljs-utils](#districtcljs-utils)
  - [merge-in](#merge-in)
  - [collify](#collify)
  - [not-nil?](#not-nil)
  - [sort-desc](#sort-desc)
  - [sort-by-desc](#sort-by-desc)
  - [map-kv-at-keys](#map-kv-at-keys)
  - [map-vals-at-keys](#map-vals-at-keys)
  - [rand-str](#rand-str)
  - [safe-assoc-in](#safe-assoc-in)
  - [js-obj->clj](#js-obj-clj)
  - [kw->str](#kw-str)
  - [transform-keys](#transform-keys)
  - [transform-vals](#transform-vals)
  

## district.cljs-utils

#### <a name="merge-in">`merge-in [& args]`
Deep merge of multiple maps
```clojure
(cljs-utils/merge-in {:a {:b 1}} {:a {:c 2}} {:a {:d 3}})
;; => {:a {:b 1 :c 2 :d 3}}
```

#### <a name="collify">`collify [x]`
Ensures a collection.
```clojure
(first (cljs-utils/collify 1))
;; => 1

(first (cljs-utils/collify [1]))
;; => 1
```

#### <a name="not-nil">`not-nil? [x]`
Returns true if not nil
```clojure
(cljs-utils/not-nil? 1)
;; => true

```

#### <a name="sort-desc">`sort-desc [coll]`
Descending sort
```clojure
(cljs-utils/sort-desc [2 1 3])
;; => [3 2 1]
```

#### <a name="sort-by-desc">`sort-by-desc [key-fn coll]`
Descending sort by key-fn
```clojure
(cljs-utils/sort-by-desc :a [{:a 2} {:a 1} {:a 3}])
;; => ({:a 3} {:a 2} {:a 1})
```

#### <a name="map-kv-at-keys">`map-kv-at-keys [f keyseq m]`
Maps a function over the key/value pairs of an associate collection, but only on given keys. 
```clojure
(cljs-utils/map-kv-at-keys #(vec [(dec %1) (inc %2)]) [8 7] {9 1, 8 2, 7 3})
;; => {9 1, 7 3, 6 4}
```

#### <a name="map-vals-at-keys">`map-vals-at-keys [f keyseq m]`
Maps a function over the values pairs of an associate collection, but only on given keys.
```clojure
(cljs-utils/map-vals-at-keys inc [:b :c] {:a 1 :b 2 :c 3})
;; => {:a 1 :b 3 :c 4}
```

#### <a name="rand-str">`rand-str [n & [{:keys [:lowercase-only? :exclude-numbers?]}]]`
Generates random alphanumeric string with `n` characters.
```clojure
(cljs-utils/rand-str 10)
;; => "LpvMscagIw"

(cljs-utils/rand-str 10 {:lowercase-only? true})
;; => "frtkeahqus"
```


#### <a name="safe-assoc-in">`safe-assoc-in [m ks v]`
Invariant version of assoc-in. Returns unchanged map if `ks` path is empty.
```clojure
(cljs-utils/safe-assoc-in {:a 1} [:b :c] 2)
;; => {:a 1}

(cljs-utils/safe-assoc-in {:b {}} [:b :c] 2)
;; => {:b {:c 2}}
```

#### <a name="js-obj-clj">`js-obj->clj [obj]`
Converts JS object (instance of a class) into Clojure map
```clojure
(:protocol (cljs-utils/js-obj->clj (aget js/window "location")))
;; => http:
```

#### <a name="kw-str">`kw->str [kw]`
Stringifies keyword. In contrary to `name`, preserves namespace as well
```clojure
(cljs-utils/kw->str :some.long/name)
;; => "some.long/name"
```

#### <a name="transform-keys">`transform-keys [t coll]`
Recursively transforms all map keys in coll with t.
```clojure
(cljs-utils/transform-keys inc {1 "a" 2 "a" 3 {4 "a"}})
;; => {2 "a" 3 "a" 4 {5 "a"}}
```

#### <a name="transform-vals">`transform-vals [t coll]`
Recursively transforms all map values in coll with t.
```clojure
(cljs-utils/transform-vals (fn [v]
                              (if (number? v)
                                (inc v)
                                v))
                            {:a 1 :b 2 :c {:z 3}})
;; => {:a 2 :b 3 :c {:z 4}}
```

## Development
```bash
lein deps
# To run tests and rerun on changes
lein doo chrome tests
```