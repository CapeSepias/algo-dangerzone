import Text.XML.HXT.Core
import Text.HandsomeSoup

url = "http://www.sports.ru/fantasy/basketball/league/10942.html"

main = do
  html <- fromUrl url
  let doc = readString [withParseHTML yes, withWarnings no] html
  links <- runX $ doc >>> css "a" ! "href"
  putStrLn links