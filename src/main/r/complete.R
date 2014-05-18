complete <- function(directory, id = 1:332) {  
  nobs <- vector(mode="numeric", length=0)
  id1 <- vector(mode="numeric", length=0)
  for (i in id) {
    if (i < 10) {      
      data <- read.csv(file=paste(directory, "/00", i, ".csv", sep=""), head=TRUE, sep=",")
    } else if(i < 100) {
      data <- read.csv(file=paste(directory, "/0", i, ".csv", sep=""), head=TRUE, sep=",")
    } else {
      data <- read.csv(file=paste(directory, "/", i, ".csv", sep=""), head=TRUE, sep=",")
    }    
    z <- complete.cases(data$sulfate, data$nitrate)
    id1 <- c(id1, i)
    nobs <- c(nobs, length(z[z==TRUE]))
  }
  data.frame(id=id1, nobs)
}