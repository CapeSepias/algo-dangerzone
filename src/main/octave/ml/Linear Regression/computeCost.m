function J = computeCost(X, y, theta)
%COMPUTECOST Compute cost for linear regression
%   J = COMPUTECOST(X, y, theta) computes the cost of using theta as the
%   parameter for linear regression to fit the data points in X and y

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
Z = X * theta;
for i = [1:m]
	J = J + (Z(i) - y(i)) ^ 2;
end
J = J / (2 * m)

end
