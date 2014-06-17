data JValue = JString String
          | JFloat Float
          | JBool Bool
          | JNull
          | JObject [(String, JValue)]
          | JArray [JValue]
            deriving (Show, Eq, Ord)


getString :: JValue -> Maybe String
getString (JString s) = Just s
getString _           = Nothing

getBool :: JValue -> Maybe Bool
getBool (JBool b) = Just b
getBool _         = Nothing

getFloat :: JValue -> Maybe Float
getFloat (JFloat f) = Just f
getFloat _          = Nothing

getObject :: JValue -> Maybe [(String, JValue)]
getObject (JObject o) = Just o
getObject _           = Nothing

getArray :: JValue -> Maybe [JValue]
getArray (JArray a) = Just a
getArray _          = Nothing

isNull :: JValue -> Bool
isNull JNull = True
isNull _     = False