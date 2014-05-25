-- count number of symbols in text file
main = interact wordCount
	where wordCount input = show (length input) ++ "\n"

