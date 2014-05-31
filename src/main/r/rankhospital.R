rankhospital <- function(state, outcome, num = "best") {
  out <- read.csv("outcome-of-care-measures.csv", colClasses = "character") ## read data
  st <- out$State
  ## check state
  if (length(st[st == state]) == 0) {
    stop("invalid state")
  }
  ## check outcome
  if (outcome == "heart attack") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Rate=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Heart.Attack))
  }
  else if (outcome == "heart failure") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Rate=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Heart.Failure))
  }
  else if (outcome == "pneumonia") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Rate=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Pneumonia))
  }
  else {
    stop("invalid outcome")
  }
  order <- order(df$Rate, df$Name, na.last=NA)
  if (num == "best") {
    rank <- 1
  } else if (num == "worst") {
    rank <- length(order)
  } else {
    rank <- num
  }
  if (rank > length(order)) {
    NA
  } 
  else {
    as.vector(df$Name)[order[rank]]
  }
}