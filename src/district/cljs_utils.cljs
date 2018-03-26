(ns district.cljs-utils
  (:require [clojure.walk :refer [postwalk]]))

(letfn [(merge-in* [a b]
          (if (map? a)
            (merge-with merge-in* a b)
            b))]
  (defn merge-in
    "Merge multiple nested maps."
    [& args]
    (reduce merge-in* nil args)))


(defn collify [x]
  (if (sequential? x) x [x]))


(defn sort-desc [coll]
  (sort #(compare %2 %1) coll))


(defn sort-by-desc [key-fn coll]
  (sort-by key-fn #(compare %2 %1) coll))


(defn map-kv-at-keys [f keyseq m]
  (let [keyseq (set keyseq)]
    (into {}
          (map (fn [[k v]]
                 (if (contains? keyseq k)
                   (f k v)
                   [k v]))
               m))))


(defn map-vals-at-keys [f keyseq m]
  (map-kv-at-keys #(vec [%1 (f %2)]) keyseq m))


(defn rand-str [n & [{:keys [:lowercase-only? :exclude-numbers?]}]]
  (let [chars-between #(map char (range (.charCodeAt %1) (inc (.charCodeAt %2))))
        chars (concat (when-not (or lowercase-only? exclude-numbers?)
                        (chars-between \0 \9))
                      (chars-between \a \z)
                      (when-not lowercase-only?
                        (chars-between \A \Z)))
        password (take n (repeatedly #(rand-nth chars)))]
    (reduce str password)))


(defn safe-assoc-in
  "Invariant version of assoc-in.
  Returns unchanged map if `ks` path is empty"
  [m ks v]
  (if (get-in m (-> ks drop-last vec))
    (assoc-in m ks v)
    m))


(defn js-obj->clj [obj]
  (reduce (fn [acc key]
            (assoc acc (keyword key) (aget obj key)))
          {}
          (js->clj (js-keys obj))))


(defn kw->str [kw]
  (when (keyword? kw)
    (subs (str kw) 1)))


(defn transform-keys [t coll]
  "Recursively transforms all map keys in coll with t."
  (letfn [(transform [[k v]] [(t k) v])]
    (postwalk (fn [x] (if (map? x) (into {} (map transform x)) x)) coll)))


(defn transform-vals [t coll]
  "Recursively transforms all map values in coll with t."
  (letfn [(transform [[k v]] [k (t v)])]
    (postwalk (fn [x] (if (map? x) (into {} (map transform x)) x)) coll)))