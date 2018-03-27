(ns tests.all
  (:require
    [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
    [district.cljs-utils :as cljs-utils]))

(deftest tests
  (is (= {:a {:b 1 :c 2 :d 3}} (cljs-utils/merge-in {:a {:b 1}} {:a {:c 2}} {:a {:d 3}})))

  (is (= 1 (first (cljs-utils/collify 1))))
  (is (= 1 (first (cljs-utils/collify [1]))))

  (is (= [3 2 1] (cljs-utils/sort-desc [2 1 3])))

  (is (= [{:a 3} {:a 2} {:a 1}] (vec (cljs-utils/sort-by-desc :a [{:a 2} {:a 1} {:a 3}]))))

  (is (= {9 1, 7 3, 6 4} (cljs-utils/map-kv-at-keys #(vec [(dec %1) (inc %2)]) [8 7] {9 1, 8 2, 7 3})))

  (is (= {:a 1 :b 3 :c 4} (cljs-utils/map-vals-at-keys inc [:b :c] {:a 1 :b 2 :c 3})))

  (let [s (cljs-utils/rand-str 10)]
    (is (true? (and (string? s)
                    (= 10 (count s))))))

  (is (= {:a 1} (cljs-utils/safe-assoc-in {:a 1} [:b :c] 2)))

  (is (= {:b {:c 2}} (cljs-utils/safe-assoc-in {:b {}} [:b :c] 2)))

  (is (string? (:protocol (cljs-utils/js-obj->clj (aget js/window "location")))))

  (is (= "some.long/name" (cljs-utils/kw->str :some.long/name)))
  (is (= "name" (cljs-utils/kw->str :name)))

  (is (= {2 "a" 3 "a" 4 {5 "a"}} (cljs-utils/transform-keys inc {1 "a" 2 "a" 3 {4 "a"}})))

  (is (= {:a 2 :b 3 :c {:z 4}} (cljs-utils/transform-vals (fn [v]
                                                            (if (number? v)
                                                              (inc v)
                                                              v))
                                                          {:a 1 :b 2 :c {:z 3}}))))


