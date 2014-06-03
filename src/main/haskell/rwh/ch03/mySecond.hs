mySecond :: [a] -> a

mySecond xs = if null (tail xs)
              then error "list too short"
              else head (tail xs)

-- main = putStrLn $ mySecond ["Hello!"]

main =  putStrLn $ mySecond ["123 ", "World!"]