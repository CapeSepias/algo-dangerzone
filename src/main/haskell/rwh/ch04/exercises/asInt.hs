import Data.Char (digitToInt)

-- bug, don't work for negative integers
asInt_fold :: String -> Int

asInt_fold (x:xs) = foldl asIntStep 0 (x:xs)

asIntStep :: Int -> Char -> Int
asIntStep acc ch = if ch == '-' then acc else acc * 10 + digitToInt ch

--The asInt_fold function uses error, so its callers cannot handle errors. Rewrite it to fix this problem
type ErrorMessage = String
asInt_either :: String -> Either ErrorMessage Int

