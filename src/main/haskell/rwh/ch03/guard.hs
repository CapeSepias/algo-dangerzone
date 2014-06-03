fromMaybe :: (Num a) => a -> Maybe a -> a

fromMaybe defaultValue wrapped =
    case wrapped of
        Nothing     -> defaultValue
        Just value  -> value