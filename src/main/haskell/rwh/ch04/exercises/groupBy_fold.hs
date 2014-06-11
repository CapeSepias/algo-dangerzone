--Use ghci to load the Data.List module and figure out what groupBy does, then write your own implementation using a fold
groupBy_fold :: (a -> a -> Bool) -> [a] -> [[a]]