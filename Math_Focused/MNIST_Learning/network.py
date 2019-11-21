import numpy as np
import random as random

class Network(object):

    def __init__(self, sizes, biases = None, weights = None):
        self.num_layers = len(sizes)
        self.sizes = sizes
        if (biases == None):
            self.biases = [np.random.randn(y, 1) for y in sizes[1:]]
        else:
            self.biases = biases
        if (weights == None):
            self.weights = [np.random.randn(y, x) for x,y in zip(sizes[:-1], sizes[1:])]
        else:
            self.weights = weights

    def feedforward(self, a):
        """network output for given 'a' input."""
        for b, w in zip(self.biases, self.weights):
            a = sigmoid(np.dot(w, a) + b)
        return a

    def SGD(self, trainData, epochs, batchSize, eta, testData = None):
        """stochatic time bois. also 'train' is a tuple of (x, y) being (inputs, expected outputs)"""
        if (testData != None):
            n_test = len(testData)
        n = len(trainData)
        for j in range(epochs):
            """go through and update w/b"""
            random.shuffle(trainData)
            miniBatches = [ trainData[ k : k + batchSize ] for k in range(0, n, batchSize) ]
            for batch in miniBatches:
                self.updateMiniBatch(batch, eta)
            if (testData != None):
                n_correct = self.evaluate(testData)
                print("Epoch {0}: {1} / {2} ({3} %)".format(j, n_correct, n_test, str(round(n_correct/n_test*100, 5))))
            else:
                print("Epoch {0} complete".format(j))

    def updateMiniBatch(self, miniBatch, eta):
        """do the actual gradient descent"""
        nabla_b = [np.zeros(b.shape) for b in self.biases]
        nabla_w = [np.zeros(w.shape) for w in self.weights]
        for x, y in miniBatch:
            delnab_b, delnab_w = self.backprop(x, y) # important one.
            nabla_b = [nb+dnb for nb, dnb in zip(nabla_b, delnab_b)]
            nabla_w = [nw+dnw for nw, dnw in zip(nabla_w, delnab_w)]
            self.weights = [w-(eta/len(miniBatch))*nw for w, nw in zip(self.weights, nabla_w)]
            self.biases = [b-(eta/len(miniBatch))*nb for b, nb in zip(self.biases, nabla_b)]

    def backprop(self, x, y):
        """Return a tuple ``(nabla_b, nabla_w)`` representing the
        gradient for the cost function C_x.  ``nabla_b`` and
        ``nabla_w`` are layer-by-layer lists of numpy arrays, similar
        to ``self.biases`` and ``self.weights``."""
        nabla_b = [np.zeros(b.shape) for b in self.biases]
        nabla_w = [np.zeros(w.shape) for w in self.weights]
        # feedforward
        activation = x
        activations = [x] # list to store all the activations, layer by layer
        zs = [] # list to store all the z vectors, layer by layer
        for b, w in zip(self.biases, self.weights):
            z = np.dot(w, activation)+b
            zs.append(z)
            activation = sigmoid(z)
            activations.append(activation)
        # backward pass
        delta = self.cost_derivative(activations[-1], y) * \
            sigmoidPrime(zs[-1])
        nabla_b[-1] = delta
        nabla_w[-1] = np.dot(delta, activations[-2].transpose())
        # Note that the variable l in the loop below is used a little
        # differently to the notation in Chapter 2 of the book.  Here,
        # l = 1 means the last layer of neurons, l = 2 is the
        # second-last layer, and so on.  It's a renumbering of the
        # scheme in the book, used here to take advantage of the fact
        # that Python can use negative indices in lists.
        for l in range(2, self.num_layers):
            z = zs[-l]
            sp = sigmoidPrime(z)
            delta = np.dot(self.weights[-l+1].transpose(), delta) * sp
            nabla_b[-l] = delta
            nabla_w[-l] = np.dot(delta, activations[-l-1].transpose())
        return (nabla_b, nabla_w)

    def cost_derivative(self, outputActivations, y):
        return (outputActivations - y)

    def evaluate(self, testData):
        test_results = [(np.argmax(self.feedforward(x)), np.argmax(y))
            for (x, y) in testData]
        return sum(int(x == y) for (x, y) in test_results)

def sigmoid(i):
    return 1/(1+np.exp(-i))

def sigmoidPrime(i):
    return sigmoid(i) * (1-(sigmoid(i)))


net = Network([2, 3, 1])