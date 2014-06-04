-- Write a function that determines whether its input list is a palindrome

myIsAPalindrome :: (Eq a) => [a] -> Bool

myIsAPalindrome [] = True
myIsAPalindrome [a] = True
myIsAPalindrome [a, b] = if a == b
                         then True
                         else False
myIsAPalindrome (b:xs) = if b == head (reverse xs)
                           then (myIsAPalindrome (init xs))
                           else False
