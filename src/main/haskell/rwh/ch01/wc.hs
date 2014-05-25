-- count number of words in text file
main = interact wordCount
	where wordCount input = show (length (words input)) ++ "\n"

