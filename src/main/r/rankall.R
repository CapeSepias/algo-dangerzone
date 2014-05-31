rankall <- function(outcome, num = "best") {
  out <- read.csv("outcome-of-care-measures.csv", colClasses = "character") ## read data
  st <- unique(out$State)
  names <- c()
  states <- c()
  for (i in 1:length(st)) {
    ## check outcome
    state <- st[i]
    states <- c(states, state)
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
      names <- c(names, NA)
    } 
    else {
      names <- c(names, as.vector(df$Name)[order[rank]])
    }
  }
  data.frame(hospital=names, state=states)
}
