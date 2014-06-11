import Data.Char (digitToInt)

-- bug, don't work for negative integers
asInt_fold :: String -> Int

asInt_fold (x:xs) = foldl asIntStep 0 (x:xs)

asIntStep :: Int -> Char -> Int
asIntStep acc ch = if ch == '-' then acc else acc * 10 + digitToInt ch