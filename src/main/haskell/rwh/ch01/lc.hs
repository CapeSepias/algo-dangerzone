-- count number of lines of input file
main = interact wordCount
	where wordCount input = show (length (lines input)) ++ "\n"
