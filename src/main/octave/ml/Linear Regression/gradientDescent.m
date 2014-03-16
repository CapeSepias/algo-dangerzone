function [theta, J_history] = gradientDescent(X, y, theta, alpha, num_iters)
%GRADIENTDESCENT Performs gradient descent to learn theta
%   theta = GRADIENTDESENT(X, y, theta, alpha, num_iters) updates theta by 
%   taking num_iters gradient steps with learning rate alpha

% Initialize some useful values
m = length(y); % number of training examples
n = length(theta);
J_history = zeros(num_iters, 1);
theta_history = zeros(m, 1);
for iter = 1:num_iters
h = X * theta;
for j = [1:n]
	sum = 0;
	for i = [1:m]
		sum = sum + ((h(i) - y(i)) * X(i, j));
	end
	theta_history(j) = theta(j) - (alpha / m) * sum;
end
for j = [1:n]
	theta(j) = theta_history(j);
end
    J_history(iter) = computeCost(X, y, theta);

end

end
