foo :: (Ord a, Num a) => a -> Maybe a

foo param = if param < const
            then Just param
            else Nothing
            where const = 100