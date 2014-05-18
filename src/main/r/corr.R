corr <- function(directory, threshold = 0) {
  cleaned <- complete(directory)
  res <- vector(mode="numeric", length=0)
  for (i in 1:332) {
    if (cleaned$nobs[i] >= threshold) {
      if (i < 10) {      
        data <- read.csv(file=paste(directory, "/00", i, ".csv", sep=""), head=TRUE, sep=",")
      } else if(i < 100) {
        data <- read.csv(file=paste(directory, "/0", i, ".csv", sep=""), head=TRUE, sep=",")
      } else {
        data <- read.csv(file=paste(directory, "/", i, ".csv", sep=""), head=TRUE, sep=",")
      }
      z <- complete.cases(data$sulfate, data$nitrate)
      res <- c(res, cor(data$sulfate[z],  data$nitrate[z]))
    }
  }
  res
}