-- file: ch03/BookStore.hs
data BookInfo = Book Int String [String]
                deriving (Show)

type CustomerID = Int
type ReviewBody = String

data BetterReview = BetterReview BookInfo CustomerID ReviewBody