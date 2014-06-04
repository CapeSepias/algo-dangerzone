-- Define a function that joins a list of lists together using a separator value
-- ghci> intersperse ',' []
--      ""
-- ghci> intersperse ',' ["foo"]
--      "foo"
-- ghci> intersperse ',' ["foo","bar","baz","quux"]
--      "foo,bar,baz,quux"

myJoin :: a -> [[a]] -> [a]
myJoin sep [x] = x
myJoin sep (x:xs) = x ++ sep : (myJoin sep xs)