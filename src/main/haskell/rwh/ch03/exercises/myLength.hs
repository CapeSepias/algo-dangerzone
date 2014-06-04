-- Write a function that computes the number of elements in a list.
-- To test it, ensure that it gives the same answers as the standard length function

myLength :: (Num b) => [a] -> b

myLength [] = 0
myLength xs = 1 + myLength (tail xs)

-- another approach
-- myLength (x:xs) = 1 + myLength (xs)