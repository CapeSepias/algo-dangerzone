best <- function(state, outcome) {
  out <- read.csv("outcome-of-care-measures.csv", colClasses = "character") ## read data
  st <- out$State
  ## check state
  if (length(st[st == state]) == 0) {
    stop("invalid state")
  }
  ## check outcome
  if (outcome == "heart attack") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Attack=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Heart.Attack))
    as.vector(df$Name)[order(df$Attack, df$Name, na.last=TRUE)[1]]
    ## x$Hospital.Name[order(x[11], x$Hospital.Name)[1]]
  }
  else if (outcome == "heart failure") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Attack=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Heart.Failure))
    as.vector(df$Name)[order(df$Attack, df$Name, na.last=TRUE)[1]]
  }
  else if (outcome == "pneumonia") {
    x <- out[which(out$State==state),]
    df <- data.frame(Name=x$Hospital.Name, Attack=as.numeric(x$Hospital.30.Day.Death..Mortality..Rates.from.Pneumonia))
    as.vector(df$Name)[order(df$Attack, df$Name, na.last=TRUE)[1]]
  }
  else {
    stop("invalid outcome")
  }  
}