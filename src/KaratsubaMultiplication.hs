-- skeleton for Karatsuba Multiplication written in Haskell

import System.IO
import Control.Monad

readAInt :: IO Int
readAInt = readLn

main = do
    inputA <- getLine
    inputB <- getLine
    putStrLn $ solve $ read input

solve n = if (n > 2 && mod n 2 == 0) then "YES" else "NO"