-- Write a function that computes the mean of a list, i.e. the sum of all elements in the list divided by its length.
-- (You may need to use the fromIntegral function to convert the length of the list from an integer into a floating point number.)

myMean :: (Num a, Fractional a) => [a] -> a

myMean [] = 0.0
myMean x = (mySum x) / fromIntegral (length x)

mySum :: (Num a) => [a] -> a
mySum [] = 0
mySum (x:xs) = x + mySum (xs)